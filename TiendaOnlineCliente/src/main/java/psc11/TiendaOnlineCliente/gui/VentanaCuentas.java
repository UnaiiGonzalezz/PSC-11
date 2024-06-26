package psc11.TiendaOnlineCliente.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import psc11.TiendaOnlineCliente.VentanaPrincipal;
import psc11.TiendaOnlineCliente.post.Usuario;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.JScrollPane;

public class VentanaCuentas extends JFrame{
    private JLabel Cuentas;
    private DefaultTableModel modeloDatosCuentas;
    protected JTable tablaCuentas;
    private JScrollPane scrollPaneCuentas;
    private JButton backButton;
    private JButton deleteButton;
    private HttpClient client = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_2).build();
    
    public VentanaCuentas() {
        setTitle("Cuentas");
        setSize(700,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuración del JLabel
        Cuentas = new JLabel("Cuentas");
        Cuentas.setFont(new Font("Arial", Font.BOLD, 20));
        Cuentas.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuración del DefaultTableModel
        modeloDatosCuentas = new DefaultTableModel();
        modeloDatosCuentas.addColumn("DNI");
        modeloDatosCuentas.addColumn("Nombre");
        modeloDatosCuentas.addColumn("Correo electrónico");
        // Aquí puedes agregar más columnas según tus necesidades

        // Configuración de la JTable
        tablaCuentas = new JTable(modeloDatosCuentas);

        // Configuración del JScrollPane para la JTable
        scrollPaneCuentas = new JScrollPane(tablaCuentas);
        
        backButton = new JButton("Atrás");
        deleteButton = new JButton("Borrar Cuenta");

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verificar si se ha seleccionado una fila en la tabla
                int filaSeleccionada = tablaCuentas.getSelectedRow();
                if (filaSeleccionada == -1) {
                    // No se ha seleccionado ninguna fila, mostrar un mensaje de advertencia
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un plato para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Obtener los datos del artículo seleccionado
                String dni = (String) tablaCuentas.getValueAt(filaSeleccionada, 0);

				if (filaSeleccionada != -1) {
					final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/cliente/borrar?dni=" + dni)).build();
                    try {
                        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        getUsuarios();
                    } catch (IOException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
				}
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.vgc.setVisible(false);
                VentanaPrincipal.va.setVisible(true);
            }
        });

        // Configuración del diseño de la ventana
        setLayout(new BorderLayout());

        // Agregar el JLabel centrado en la parte superior
        add(Cuentas, BorderLayout.NORTH);

        // Agregar la tabla con el JScrollPane en el centro
        add(scrollPaneCuentas, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(backButton);
        bottomPanel.add(deleteButton);

        // Establecer tamaño preferido para los botones
        backButton.setPreferredSize(new Dimension(Short.MAX_VALUE, backButton.getPreferredSize().height));
        deleteButton.setPreferredSize(new Dimension(Short.MAX_VALUE, deleteButton.getPreferredSize().height));
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    public void getUsuarios() {
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/cliente/all")).build();

        try {
            // Realizar petición al servidor
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Usuario> usuarios = convertirObjeto(response.body(), new TypeReference<List<Usuario>>() { });
            modeloDatosCuentas.setRowCount(0);
            usuarios.stream().forEach(usuario -> {
                modeloDatosCuentas.addRow(new Object[] {usuario.getDni(), usuario.getNombre(), usuario.getCorreo()});
            });
            
            this.tablaCuentas.setModel(modeloDatosCuentas);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    final ObjectMapper mapper = new ObjectMapper();
    public <T> T convertirObjeto(final String json, final TypeReference<T> reference) {
        try {
           return this.mapper.readValue(json, reference);
        } catch (IOException e) {
            e.printStackTrace();
            {
                return null;
            }
        }
    }   
    }
