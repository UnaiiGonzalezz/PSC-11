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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import psc11.TiendaOnlineCliente.VentanaPrincipal;

import psc11.TiendaOnlineCliente.post.Pedido;

public class VentanaEnvio extends JFrame{
    private HttpClient client = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_2).build();
    private JTable tablaEnvios;

    public VentanaEnvio() {
        this.setLayout(new BorderLayout());
    
        // Nombres de las columnas
        String[] columnNames = {"Id", "Estado", "Envio", "DNI"};
    
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
                    } else if ("Entregado".equals(estado)) {
                        c.setBackground(new Color(107, 249, 88));
                    } else if ("Cancelado".equals(estado)){
                        c.setBackground(new Color(255,0,0));
                    } else {
                        c.setBackground(getBackground());
                    }
                }

                return c;
            }
        };
    
        
        // Llamar a getEnvios() para cargar los envíos en la tabla
        getEnvios();
    
        JButton btnAtras = new JButton("ATRAS");
        btnAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.ve.setVisible(false);
                VentanaPrincipal.va.setVisible(true);
            }
        });
    
        JButton botonEstado = new JButton("Cambiar Estado");
        botonEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaEnvios.getSelectedRow();
                if (selectedRow != -1) {
                    String correoEnvio = tablaEnvios.getValueAt(selectedRow, 0).toString();
                    String estadoActual = tablaEnvios.getValueAt(selectedRow, 1).toString();
                    mostrarDialogoEstado(correoEnvio, estadoActual, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un envío de la tabla.");
                }
            }
        });
    
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(btnAtras);
        panelBotones.add(botonEstado);
    
        this.add(panelBotones, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(tablaEnvios);
        this.add(scrollPane, BorderLayout.CENTER);
    
        this.setSize(800, 600);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void getEnvios() {
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/pedido/all")).build();
    
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Pedido> envios = convertirObjeto(response.body(), new TypeReference<List<Pedido>>() { });
            DefaultTableModel model = (DefaultTableModel) tablaEnvios.getModel();
            model.setRowCount(0);
            envios.stream().forEach(envio -> {
                ((DefaultTableModel) tablaEnvios.getModel()).addRow(new Object[] {envio.getId(), envio.getEstado().toString(), envio.getUsuario().getCorreo(), envio.getUsuario().getDni()});
            });
            this.tablaEnvios.setModel((DefaultTableModel) tablaEnvios.getModel());
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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

    private void mostrarDialogoEstado(String correoEnvio, String estadoActual, int selectedRow) {
        JDialog dialog = new JDialog(this, "Cambiar Estado del Envío", true);
        dialog.setLayout(new BorderLayout());
    
        String[] estados = {"Preparacion", "Reparto", "Recibido"};
        JComboBox<String> comboBox = new JComboBox<>(estados);
        comboBox.setSelectedItem(estadoActual);
    
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nuevoEstado = comboBox.getSelectedItem().toString();
                tablaEnvios.setValueAt(nuevoEstado, selectedRow, 1);
                updateEstado(tablaEnvios.getModel().getValueAt(selectedRow, 3).toString(), nuevoEstado,Integer.parseInt(tablaEnvios.getModel().getValueAt(selectedRow, 0).toString()));
                // Aquí puedes añadir la lógica para actualizar el estado en el servidor
                dialog.dispose();
            }
        });
    
        dialog.add(comboBox, BorderLayout.CENTER);
        dialog.add(btnAceptar, BorderLayout.SOUTH);
    
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}