package psc11.TiendaOnlineCliente.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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

import psc11.TiendaOnlineCliente.VentanaPrincipal;
import psc11.TiendaOnlineCliente.post.Plato;

public class VentanaProductos extends JFrame {
    private HttpClient client = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_2).build();
    private static final String JLabel = null;
    private JTable tablaProductos;
    private List<Plato> todosLosPlatos = new ArrayList<>();
    private JButton botonAgregar, botonEliminar, botonEditar, backButton, añadirCarrito, misPedidos;
    private JLabel labelCalzado, labelRopaDeportiva, labelCalzadoDeportivo, labelRopa, labelAccesorios, labelRopaInterior, verTodo, labelFiltroDineroMax, labelFiltroTalla, labelFiltroDineroMin, labelFiltroTallaCalzado;
    private JButton carrito;
    private List<Plato> productosCarrito = new ArrayList<>();


    public VentanaProductos() {
        setTitle("Lista de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
    
        // Crear tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Tamaño", "Categoría"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
        tablaProductos = new JTable(modeloTabla);
    
        // Cambiar la fuente de la tabla
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 14));
    
        // Crear botones
        carrito = new JButton("");
        ImageIcon carritoIcon = new ImageIcon("resources/carrito2.jpg");
//		strava.setIcon(new ImageIcon("resources/strava.png"));
		Image carritoImage = carritoIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		carrito.setIcon(new ImageIcon(carritoImage));

        botonAgregar = new JButton("Agregar");
        botonEliminar = new JButton("Eliminar");
        botonEditar = new JButton("Editar");
        backButton = new JButton("Atrás");
        añadirCarrito = new JButton("Añadir al carrito");
        misPedidos = new JButton("Mis Pedidos");

        labelCalzado = new JLabel("Calzado");
        labelRopaDeportiva = new JLabel("Ropa Deportiva");
        labelCalzadoDeportivo = new JLabel("Calzado Deportivo");
        labelRopa = new JLabel("Ropa");
        labelAccesorios = new JLabel("Accesorios");
        labelRopaInterior = new JLabel("Ropa Interior");
        verTodo = new JLabel("Ver Todo");

        labelFiltroDineroMax = new JLabel("Filtrar por precio máximo");
        labelFiltroDineroMin = new JLabel("Filtrar por precio minimo");
        labelFiltroTalla = new JLabel("Filtrar por talla");
        labelFiltroTallaCalzado = new JLabel("Filtrar por talla Calzado");

        // Agregar botones al panel
        JPanel panelBotones = new JPanel();
        JPanel panelCategoria = new JPanel();
        JPanel panelFiltros = new JPanel();
        JPanel panelFiltros1 = new JPanel();
        JPanel panelFiltros2 = new JPanel();
        JPanel panelFiltros3 = new JPanel();
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));

        // Inicializar JSlider con un rango de precios
        JSlider sliderMax = new JSlider(JSlider.HORIZONTAL, 0, 400, 0);

        JSlider sliderMin = new JSlider(JSlider.HORIZONTAL, 0, 400, 0);

        JLabel labelPrecioMax = new JLabel();

        sliderMax.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    Integer precioObj = (Integer) source.getValue();
                    if (precioObj != null) {
                        int precio = precioObj.intValue();
                
                        labelPrecioMax.setText(precio + "€");
                
                        int precioMin = sliderMin.getValue();
                        int precioMax = sliderMax.getValue();
                        filtrarProductosPorPrecio(precioMin, precioMax);
                    }
                }
            }
        });

        JLabel labelPrecioMin = new JLabel();

        sliderMin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    Integer precioObj = (Integer) source.getValue();
                    if (precioObj != null) {
                        int precio = precioObj.intValue();
                
                        labelPrecioMin.setText(precio + "€");
                
                        int precioMin = sliderMin.getValue();
                        int precioMax = sliderMax.getValue();
                        filtrarProductosPorPrecio(precioMin, precioMax);
                    }
                }
            }
        });

        // Crear JComboBox para filtrar por talla
        String[] tallas = { "-", "XS", "S", "M", "L", "XL" };
        JComboBox<String> comboBoxTallas = new JComboBox<>(tallas);

        comboBoxTallas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Paso 2: Obtener el valor seleccionado del JComboBox
                String tallaSeleccionada = (String) comboBoxTallas.getSelectedItem();
        
                filtrarProductosPorTalla(tallaSeleccionada);
            }
        });

        String[] tallasCalzado2 = { "-", "37", "38", "39", "40", "41", "42", "43", "44"};
        JComboBox<String> comboBoxTallasCalzado = new JComboBox<>(tallasCalzado2);

        comboBoxTallasCalzado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tallaSeleccionada = (String) comboBoxTallasCalzado.getSelectedItem();

                filtrarProductosPorTalla(tallaSeleccionada);
            }
        });


        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));

        panelFiltros.add(panelFiltros1);
        panelFiltros.add(panelFiltros2);
        panelFiltros.add(panelFiltros3);

        panelFiltros1.add(labelFiltroDineroMax);
        panelFiltros1.add(sliderMax);
        panelFiltros1.add(labelPrecioMax);

        panelFiltros2.add(labelFiltroDineroMin);
        panelFiltros2.add(sliderMin);
        panelFiltros2.add(labelPrecioMin);
        
        panelFiltros3.add(labelFiltroTalla);
        panelFiltros3.add(comboBoxTallas);
        panelFiltros3.add(labelFiltroTallaCalzado);
        panelFiltros3.add(comboBoxTallasCalzado);

        panelNorte.add(panelCategoria);
        panelNorte.add(panelFiltros);

        panelBotones.add(backButton);
        panelCategoria.add(labelCalzado);
        panelCategoria.add(labelRopaDeportiva);
        panelCategoria.add(labelCalzadoDeportivo);
        panelCategoria.add(labelRopa);
        panelCategoria.add(labelAccesorios);
        panelCategoria.add(labelRopaInterior);
        panelCategoria.add(verTodo);
        panelCategoria.add(carrito, BorderLayout.EAST);

        // Establecer estilos para cada JLabel
        Font newFont = new Font("Arial", Font.BOLD, 12); // Nueva fuente más pequeña
        Color newForegroundColor = Color.WHITE; // Nuevo color de primer plano
        Color newBackgroundColor = Color.DARK_GRAY; // Nuevo color de fondo
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Nuevo borde


        

        // Aplica los nuevos estilos a cada JLabel
        verTodo.setFont(newFont);
        verTodo.setForeground(newForegroundColor);
        verTodo.setBackground(newBackgroundColor);
        verTodo.setBorder(border);
        verTodo.setHorizontalAlignment(SwingConstants.CENTER);
        verTodo.setOpaque(true);


        labelCalzado.setFont(newFont);
        labelCalzado.setForeground(newForegroundColor);
        labelCalzado.setBackground(newBackgroundColor);
        labelCalzado.setBorder(border);
        labelCalzado.setHorizontalAlignment(SwingConstants.CENTER);
        labelCalzado.setOpaque(true);

        labelRopaDeportiva.setFont(newFont);
        labelRopaDeportiva.setForeground(newForegroundColor);
        labelRopaDeportiva.setBackground(newBackgroundColor);
        labelRopaDeportiva.setBorder(border);
        labelRopaDeportiva.setHorizontalAlignment(SwingConstants.CENTER);
        labelRopaDeportiva.setOpaque(true);

        labelCalzadoDeportivo.setFont(newFont);
        labelCalzadoDeportivo.setForeground(newForegroundColor);
        labelCalzadoDeportivo.setBackground(newBackgroundColor);
        labelCalzadoDeportivo.setBorder(border);
        labelCalzadoDeportivo.setHorizontalAlignment(SwingConstants.CENTER);
        labelCalzadoDeportivo.setOpaque(true);

        labelRopa.setFont(newFont);
        labelRopa.setForeground(newForegroundColor);
        labelRopa.setBackground(newBackgroundColor);
        labelRopa.setBorder(border);
        labelRopa.setHorizontalAlignment(SwingConstants.CENTER);
        labelRopa.setOpaque(true);

        labelAccesorios.setFont(newFont);
        labelAccesorios.setForeground(newForegroundColor);
        labelAccesorios.setBackground(newBackgroundColor);
        labelAccesorios.setBorder(border);
        labelAccesorios.setHorizontalAlignment(SwingConstants.CENTER);
        labelAccesorios.setOpaque(true);

        labelRopaInterior.setFont(newFont);
        labelRopaInterior.setForeground(newForegroundColor);
        labelRopaInterior.setBackground(newBackgroundColor);
        labelRopaInterior.setBorder(border);
        labelRopaInterior.setHorizontalAlignment(SwingConstants.CENTER);
        labelRopaInterior.setOpaque(true);


        labelCalzado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrarProductosPorCategoria("Calzado");
            }
        });
        
        labelRopaDeportiva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrarProductosPorCategoria("RopaDeportiva");
            }
        });

        labelCalzadoDeportivo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrarProductosPorCategoria("CalzadoDeportivo");
            }
        });

        labelRopa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrarProductosPorCategoria("Ropa");
            }
        });

        labelAccesorios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrarProductosPorCategoria("Accesorios");
            }
        });

        labelRopaInterior.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrarProductosPorCategoria("RopaInterior");
            }
        });

        verTodo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Llama al método getProductos para cargar todos los productos en la tabla
                getProductos();
            }
        });

        //FUNCIONALIDAD DEL BOTON CARRITO
        carrito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Cuando se hace clic en el botón "Carrito"
                VentanaCarrito vcarrito = new VentanaCarrito(productosCarrito);
                vcarrito.setVisible(true);
            }
        });

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cuando se hace clic en la tabla de productos
                int filaSeleccionada = tablaProductos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener el producto seleccionado
                    Plato producto = todosLosPlatos.get(filaSeleccionada);
                    // Agregar el producto al carrito
                    productosCarrito.add(producto);
                }
            }
        });
    




        if (VentanaPrincipal.admin) {
            panelBotones.add(botonAgregar);
            panelBotones.add(botonEliminar);
            panelBotones.add(botonEditar);
        }

        if (!VentanaPrincipal.admin){
            panelBotones.add(añadirCarrito);
            panelBotones.add(misPedidos);
        }
        
        botonEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verificar si se ha seleccionado una fila en la tabla
                int filaSeleccionada = tablaProductos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    // No se ha seleccionado ninguna fila, mostrar un mensaje de advertencia
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un plato para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Obtener los datos del plato seleccionado
                Integer id = (Integer) tablaProductos.getValueAt(filaSeleccionada, 0);
                String nombre = (String) tablaProductos.getValueAt(filaSeleccionada, 1);
                String categoria = (String) tablaProductos.getValueAt(filaSeleccionada, 5);
                String descripcion = (String) tablaProductos.getValueAt(filaSeleccionada, 2);
                double precio = (double) tablaProductos.getValueAt(filaSeleccionada, 3);
                String tamaño = (String) tablaProductos.getValueAt(filaSeleccionada, 4);

                // Crear componentes para editar el plato
                JTextField nombreField = new JTextField(nombre);
                JComboBox<String> categoriaCombo = new JComboBox<>();
                // Agregar opciones al combo de categoría y seleccionar la categoría actual
                categoriaCombo.addItem("Primero");
                categoriaCombo.addItem("Segundo");
                categoriaCombo.addItem("Postre");
                categoriaCombo.addItem("Batido");
                categoriaCombo.addItem("Entrante");
                categoriaCombo.setSelectedItem(categoria);
                JTextField descripcionField = new JTextField(descripcion);
                JSpinner precioSpinner = new JSpinner(new SpinnerNumberModel(precio, 0, 500, 1));
                JTextField tamañoField = new JTextField(tamaño);

                // Crear el JComponent con los componentes necesarios
                JComponent[] inputs = new JComponent[] {
                    new JLabel("Nombre: "),
                    nombreField,
                    new JLabel("Categoría: "),
                    categoriaCombo,
                    new JLabel("Descripción: "),
                    descripcionField,
                    new JLabel("Precio: "),
                    precioSpinner,
                    new JLabel("Tamaño: "),
                    tamañoField
                };

                // Mostrar el JComponent para editar el plato
                int result = JOptionPane.showConfirmDialog(null, inputs, "Editar Plato", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/articulo/update?id=" + id + "&nombre=" + nombreField.getText() +"&desc=" + descripcionField.getText() + "&categoria=" + categoriaCombo.getSelectedItem().toString() + "&precio=" + precioSpinner.getValue() + "&tam="+tamañoField.getText())).build();
                    try {
                        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.vprod.setVisible(false);
                if (VentanaPrincipal.admin) {
                    VentanaPrincipal.va.setVisible(true);
                } else{
                    VentanaPrincipal.vp.setVisible(true);
                }
                
            }
        });

        botonEliminar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {

                int filaSeleccionada = tablaProductos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    // No se ha seleccionado ninguna fila, mostrar un mensaje de advertencia
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un plato para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Integer id = (Integer) tablaProductos.getValueAt(filaSeleccionada, 0);

				if (filaSeleccionada != -1) {
					final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/articulo/borrar?id=" + id)).build();
                    try {
                        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        getProductos();
                    } catch (IOException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
				}
				
				
			}
		});

        misPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear una nueva ventana de pedidos
                VentanaPedido ventanaPedidos = new VentanaPedido();
        
                // Hacer que la ventana de pedidos sea visible
                ventanaPedidos.setVisible(true);
        
                // Hacer que la ventana principal sea invisible
                setVisible(false);
            }
        });

        botonAgregar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JTextField nombre_art = new JTextField(30);
				JComboBox<String> categoriaCombo = new JComboBox<String>();
				categoriaCombo.addItem("Primero");
				categoriaCombo.addItem("Segundo");
				categoriaCombo.addItem("Postre");
				categoriaCombo.addItem("Batido");
				categoriaCombo.addItem("Entrante");
				JTextField desc_art = new JTextField(30);
				JSpinner precio = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
				JTextField tamaño = new JTextField(30);
				
				JComponent[] inputs = new JComponent[] {
						new JLabel("NOMBRE: "),
						nombre_art,
						new JLabel("CATEGORIA: "),
						categoriaCombo,
						new JLabel("DESCRIPCION: "),
						desc_art,
						new JLabel("PRECIO: "),
						precio,
						new JLabel("TAMAÑO: "),
						tamaño,
						
					};
				int result = JOptionPane.showConfirmDialog(null, inputs, 
						"AGREGAR PLATO", 
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				
				if (result == JOptionPane.OK_OPTION) {
					final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/articulo/crear?nombre=" + nombre_art.getText() +"&desc=" + desc_art.getText() + "&categoria=" + categoriaCombo.getSelectedItem().toString() + "&precio=" + precio.getValue() + "&tam="+tamaño.getText())).build();
                    try {
                        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        getProductos();
                    } catch (IOException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
				}
				
				
			}
		});

        // Agregar componentes a la ventana
        getContentPane().setLayout(new BorderLayout());
        //getContentPane().add(panelInfo, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        getContentPane().add(panelNorte, BorderLayout.NORTH);

    }

    private void filtrarProductosPorCategoria(String categoria) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaProductos.getModel();
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar productos filtrados
    
        if (categoria.equals("Todos")) {
            // Restaurar la tabla completa usando la copia de respaldo de todos los productos
            todosLosPlatos.forEach(plato -> {
                modeloTabla.addRow(new Object[] {plato.getId(), plato.getNombre(), plato.getDescripcion(), plato.getPrecio(), plato.getTamano(), plato.getCategoria()});
            });
        } else {
            // Filtrar productos por categoría seleccionada
            todosLosPlatos.stream()
                    .filter(plato -> plato.getCategoria().equals(categoria))
                    .forEach(plato -> {
                        modeloTabla.addRow(new Object[]{plato.getId(), plato.getNombre(), plato.getDescripcion(), plato.getPrecio(), plato.getTamano(), plato.getCategoria()});
                    });
        }
    }

    public void getProductos() { 
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:8080/articulo/all")).build();
        
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Plato> articulos = convertirObjeto(response.body(), new TypeReference<List<Plato>>() { });
            todosLosPlatos = new ArrayList<>(platos);
            DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
            model.setRowCount(0);
            articulos.stream().forEach(plato -> {
                ((DefaultTableModel) tablaProductos.getModel()).addRow(new Object[] {plato.getId(), plato.getNombre(), plato.getDescripcion(), plato.getPrecio(), plato.getTamano(), plato.getCategoria()});
            });
            this.tablaProductos.setModel((DefaultTableModel) tablaProductos.getModel());
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

    public void filtrarProductosPorPrecioMax(int precio) {
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();

        // Crear un nuevo RowFilter que filtre las filas basándose en el precio
        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                Double precioProductoDouble = (Double) entry.getValue(3);
                if (precioProductoDouble != null) {
                    int precioProducto = precioProductoDouble.intValue();
                    return precioProducto <= precio;
                } else {
                    return false;
                }
            }
        };

        // Aplicar el filtro al RowSorter de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        sorter.setRowFilter(filter);
        tablaProductos.setRowSorter(sorter);
    }

    public void filtrarProductosPorPrecio(int precioMin, int precioMax) {
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
    
        // Crear un nuevo RowFilter que filtre las filas basándose en el precio
        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                Double precioProductoDouble = (Double) entry.getValue(3);
                if (precioProductoDouble != null) {
                    int precioProducto = precioProductoDouble.intValue();
                    return precioProducto >= precioMin && precioProducto <= precioMax;
                } else {
                    return false;
                }
            }
        };
    
        // Aplicar el filtro al RowSorter de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        sorter.setRowFilter(filter);
        tablaProductos.setRowSorter(sorter);
    }

    // public void filtrarProductosPortamano(String tallaSeleccionada) {
    //     DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
    //     TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    
    //     RowFilter<DefaultTableModel, Object> rowFilter = null;
    //     if (!tallaSeleccionada.equals("-")) {
    //         int indiceColumnaTalla = 4;
    //         rowFilter = new RowFilter<DefaultTableModel, Object>() {
    //             public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
    //                 String cellValue = entry.getStringValue(indiceColumnaTalla);
    //                 return cellValue.equals(tallaSeleccionada);
    //             }
    //         };
    //     }
    
    //     sorter.setRowFilter(rowFilter);
    //     tablaProductos.setRowSorter(sorter);
    // }
}
