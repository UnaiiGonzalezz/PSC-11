package psc11.TiendaOnlineCliente.gui;

import java.net.http.HttpClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import psc11.TiendaOnlineCliente.VentanaPrincipal;
import psc11.TiendaOnlineCliente.post.Plato;
import psc11.TiendaOnlineCliente.post.Pedido;

public class VentanaPedido extends JFrame {
    private VentanaPrincipal vp;
    private HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2).build();
    private JTable tablaEnvios;

    public VentanaPedido() {
        this.setLayout(new BorderLayout());

        // Nombres de las columnas
        String[] columnNames = {"Envío", "Estado", "DNI"};

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Crear la tabla con el modelo
        tablaEnvios = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Asegúrate de que el color solo se cambie para las filas no seleccionadas
                if (!isRowSelected(row)) {
                    // Obtén el estado del envío para esta fila
                    String estado = getModel().getValueAt(row, 1).toString();

                    // Cambia el color de fondo de la fila en función del estado
                    if ("Preparacion".equals(estado)) {
                        c.setBackground(new Color(255, 180, 79));
                    } else if ("Reparto".equals(estado)) {
                        c.setBackground(new Color(110, 176, 246));
                    } else if ("Cancelado".equals(estado)) {
                        c.setBackground(new Color(107, 249, 88));
                    } else if ("Entregado".equals(estado)){
                        c.setBackground(new Color(255,0,0));
                    } else {
                        c.setBackground(getBackground());
                    }
                }

                return c;
            }
        };

        // Llamar a getEnvios() para cargar los envíos en la tabla
        String dni = VentanaPrincipal.vp.getDniUsuario();
        
        getEnvio(dni);
        

        JButton btnAtras = new JButton("ATRAS");
        btnAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPedido.this.setVisible(false);
                VentanaPrincipal.vprod.setVisible(true);
            }
        });

        JButton botonCancelar = new JButton("Cancelado");
        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaEnvios.getSelectedRow();
                if (selectedRow != -1) {
                    JDialog jdialogCancelar = new JDialog(VentanaPedido.this, "Cancelar Pedido", true);
                    jdialogCancelar.setLayout(new BorderLayout());

                    // Crear las opciones de cancelacion
                    JPanel panelOpciones = new JPanel();
                    panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
                    JRadioButton opcion1 = new JRadioButton("No es lo que queria pedir");
                    JRadioButton opcion2 = new JRadioButton("Soy alergico al plato");
                    JRadioButton opcion3 = new JRadioButton("No me inspira confianza");

                    // Agrupar los botones de radio
                    ButtonGroup group = new ButtonGroup();
                    group.add(opcion1);
                    group.add(opcion2);
                    group.add(opcion3);

                    panelOpciones.add(opcion1);
                    panelOpciones.add(opcion2);
                    panelOpciones.add(opcion3);

                    // Añadir caja de texto para otras razones
                    JTextArea cajaTexto = new JTextArea(5, 20);
                    cajaTexto.setLineWrap(true);
                    cajaTexto.setWrapStyleWord(true);
                    JScrollPane scrollPane = new JScrollPane(cajaTexto);

                    panelOpciones.add(new JLabel("Otras razones:"));
                    panelOpciones.add(scrollPane);

                    jdialogCancelar.add(panelOpciones, BorderLayout.CENTER);

                    // Botón para confirmar la cancelacion
                    JButton btnConfirmar = new JButton("Confirmar cancelacion");
                    btnConfirmar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            tablaEnvios.getModel().setValueAt("Cancelado", selectedRow, 1);
                            updateEstado(tablaEnvios.getModel().getValueAt(selectedRow, 2).toString(), "Devuelto",Integer.parseInt(tablaEnvios.getModel().getValueAt(selectedRow, 0).toString()));
                            // Lógica para procesar la cancelacion
                            if (opcion1.isSelected()) {
                                System.out.println("Cancelacion seleccionada: No es lo que queria pedir");
                            } else if (opcion2.isSelected()) {
                                System.out.println("Cancelacion seleccionada: Soy alergico al plato");
                            } else if (opcion3.isSelected()) {
                                System.out.println("Cancelacion seleccionada: No me inspira confianza");
                            }

                            String otrasRazones = cajaTexto.getText();
                            if (!otrasRazones.isEmpty()) {
                                System.out.println("Otras razones para la cancelacion: " + otrasRazones);
                            }
                            jdialogCancelar.dispose();
                        }
                    });

                    jdialogCancelar.add(btnConfirmar, BorderLayout.SOUTH);
                    jdialogCancelar.setSize(300, 300);
                    jdialogCancelar.setLocationRelativeTo(VentanaPedido.this);
                    jdialogCancelar.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(VentanaPedido.this, "Seleccione un pedido para cancelar.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(btnAtras);
        panelBotones.add(botonCancelar);

        this.add(panelBotones, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(tablaEnvios);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(800, 600);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void getEnvio(String dniUsuario) {
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/pedido/all")).build();
        System.out.println(dniUsuario + "dni");
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Pedido> envios = convertirObjeto(response.body(), new TypeReference<List<Pedido>>() {});
            DefaultTableModel model = (DefaultTableModel) tablaEnvios.getModel();
            model.setRowCount(0);
            // Filtrar los pedidos por el DNI del usuario
            List<Pedido> enviosDelUsuario = envios.stream()
                    .filter(envio -> envio.getUsuario().getDni().equals(dniUsuario))
                    .collect(Collectors.toList());

            enviosDelUsuario.stream().forEach(envio -> {
                ((DefaultTableModel) tablaEnvios.getModel()).addRow(new Object[]{envio.getId(), envio.getEstado().toString(), envio.getUsuario().getDni()});
            });
            this.tablaEnvios.setModel((DefaultTableModel) tablaEnvios.getModel());

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

    
    public void updateEstado(String dni, String estado, int idPedido) {
        final HttpRequest request = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.noBody())
            .uri(URI.create("http://127.0.0.1:8080/pedido/update?dni=" + dni + "&estado=" + estado + "&id=" + idPedido))
            .build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Puedes procesar la respuesta aquí...
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
