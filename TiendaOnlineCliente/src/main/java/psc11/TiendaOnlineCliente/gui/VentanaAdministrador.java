package psc11.TiendaOnlineCliente.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.*;

import psc11.TiendaOnlineCliente.VentanaPrincipal;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VentanaAdministrador extends JFrame{
    protected JButton gestionarProductos;
    protected JButton gestionarCuentas;
    JButton gestionarEnvios;
    
    protected JButton backButton;
    public static VentanaProductos vprod;

    public VentanaAdministrador() {

        setTitle("VENTANA ADMIN");
        setSize(900, 450);
        setLocationRelativeTo(null);
        Container cp = this.getContentPane();
        cp.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel panelBotones = new JPanel(new GridLayout(1, 2));
        cp.add(panelBotones, gbc);

        gestionarProductos = new JButton("GESTIONAR PRODUCTOS");
        gestionarCuentas = new JButton("GESTIONAR CUENTAS");
        gestionarEnvios = new JButton("GESTIONAR ENVÍOS");
        backButton = new JButton("Atrás");

        Color buttonColor = new Color(140, 170, 255);
        gestionarProductos.setBackground(buttonColor);
        gestionarProductos.setBorder(new LineBorder(Color.BLACK));
        gestionarCuentas.setBackground(buttonColor);
        gestionarCuentas.setBorder(new LineBorder(Color.BLACK));
        gestionarEnvios.setBackground(buttonColor);
        gestionarEnvios.setBorder(new LineBorder(Color.BLACK));
         

        gestionarProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.vprod = new VentanaProductos();
                VentanaPrincipal.va.setVisible(false);
                VentanaPrincipal.vprod.getProductos();
                VentanaPrincipal.vprod.setVisible(true);
            }
        });

        gestionarCuentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.vgc = new VentanaCuentas();
                VentanaPrincipal.vgc.getUsuarios();
                VentanaPrincipal.va.setVisible(false);
                VentanaPrincipal.vgc.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.va.setVisible(false);
                VentanaPrincipal.vp.setVisible(true);
            }
        });

        gestionarEnvios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.ve = new VentanaEnvio();
                //VentanaPrincipal.vgc.getPedidos();
                VentanaPrincipal.va.setVisible(false);
                VentanaPrincipal.ve.setVisible(true);
            }
        });

        getContentPane().setLayout(new BorderLayout());

        panelBotones.add(gestionarProductos);
        panelBotones.add(gestionarCuentas);
        panelBotones.add(gestionarEnvios);

        add(panelBotones, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }


}
