package psc11.TiendaOnlineCliente.gui;

import psc11.TiendaOnlineCliente.VentanaPrincipal;
import psc11.TiendaOnlineCliente.post.Plato;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaCarrito extends JFrame{
     private VentanaPrincipal vp;
    private HttpClient client = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_2).build();
    private List<Plato> productosCarrito;
    private JTable tablaCarrito;
    private JButton pagarButton = new JButton("Finalizar y Pagar");
    JButton eliminarButton = new JButton("Eliminar");
    JPanel buttonPanel = new JPanel();
    DefaultTableModel modeloTabla;

    public VentanaCarrito(List<Plato> productosCarrito) {
        this.productosCarrito = productosCarrito;

        setTitle("Carrito de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Crear tabla para mostrar los productos del carrito
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Tamaño", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCarrito = new JTable(modeloTabla);

        // Llenar la tabla con los productos del carrito
        for (Plato plato : productosCarrito) {
            Object[] fila = {
                plato.getId(),
                plato.getNombre(),
                plato.getDescripcion(),
                plato.getPrecio(),
                plato.getTamano(),
                plato.getCategoria()
            };
            modeloTabla.addRow(fila);
        }

    //Boton eliminar producto del carrito


    eliminarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener la fila seleccionada
            int selectedRow = tablaCarrito.getSelectedRow();

            // Si hay una fila seleccionada, eliminarla
            if (selectedRow != -1) {
                modeloTabla.removeRow(selectedRow);
                productosCarrito.remove(selectedRow);
            }
        }
    });

    pagarButton.addActionListener(new ActionListener() {
    @Override
        public void actionPerformed(ActionEvent e) {
            crearPedido();
            JDialog dialog = new JDialog();
            dialog.setModal(true);
            dialog.setSize(300, 225);
            dialog.setLocationRelativeTo(null);

            // Crear un nuevo JButton
            JButton confirmarButton = new JButton("Confirmar e imprimir ticket");
            confirmarButton.setPreferredSize(new Dimension(200, 50));

            // Crear los JRadioButton
            JRadioButton opcion1 = new JRadioButton("Envio a domicilio");
            opcion1.setFont(new Font("Arial", Font.BOLD, 15));
            JRadioButton opcion2 = new JRadioButton("Recoger en tienda");
            opcion2.setFont(new Font("Arial", Font.BOLD, 15));

            // Crear el ButtonGroup y agregar los JRadioButton
            ButtonGroup grupoOpciones = new ButtonGroup();
            grupoOpciones.add(opcion1);
            grupoOpciones.add(opcion2);

            // Crear el JLabel
            JLabel textoOpcionesEnvio = new JLabel("Opciones de envío", SwingConstants.CENTER);
            textoOpcionesEnvio.setFont(new Font("Arial", Font.BOLD, 20));
            textoOpcionesEnvio.setPreferredSize(new Dimension(200, 50));

            // Crear un panel para los JRadioButton con BoxLayout
            JPanel panelOpciones = new JPanel();
            panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
            panelOpciones.add(opcion1);
            panelOpciones.add(opcion2);

            // Crear un panel principal con BorderLayout
            JPanel panelPrincipal = new JPanel(new BorderLayout());
            panelPrincipal.add(textoOpcionesEnvio, BorderLayout.NORTH);
            panelPrincipal.add(panelOpciones, BorderLayout.CENTER);
            panelPrincipal.add(confirmarButton, BorderLayout.SOUTH);

            // Añadir el panel principal al JDialog
            dialog.add(panelPrincipal);

            confirmarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implementar la lógica para imprimir el ticket
                    imprimirTicket();
                    dialog.setVisible(false);
                    VentanaCarrito.this.dispose();
                }
            });

            // Mostrar el JDialog
            dialog.setVisible(true);
        }
    });

        // Agregar la tabla a la ventana
        buttonPanel.add(eliminarButton);
        buttonPanel.add(pagarButton);
        getContentPane().add(tablaCarrito);
        getContentPane().add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void imprimirTicket() {
        try {
            PrintWriter writer = new PrintWriter("ticket.txt", "UTF-8");

            double total = 0.0;
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String nombrePlato = modeloTabla.getValueAt(i, 1).toString();
                
                int cantidad = 1;
                
                double precio = 0.0;
                if (isNumeric(modeloTabla.getValueAt(i, 3).toString())) {
                    precio = Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());
                    total += precio;
                }
        
                // Escribir los detalles del artículo en el archivo
                writer.println("Plato " + (i+1));
                writer.println("Nombre: " + nombrePlato);
                writer.println("Cantidad: " + cantidad);
                writer.println("Precio: " + precio);
                writer.println("-------------------------");
            }

            writer.println("Total: " + total);

            // Obtener la fecha y hora actual
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Escribir la fecha y hora en el archivo
            writer.println("Fecha y hora: " + now.format(formatter));

            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void crearPedido() {
        String dni = VentanaPrincipal.vp.getDniUsuario();
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/pedido/crear?dni=" + dni)).build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Puedes procesar la respuesta aquí...
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
