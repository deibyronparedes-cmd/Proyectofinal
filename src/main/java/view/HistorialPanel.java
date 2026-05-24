package view;

import controller.SeñaController;
import controller.HistorialManager.EntradaHistorial;
import repository.HistorialDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class HistorialPanel extends JPanel {

    private final SeñaController controller;
    private final MainFrame frame;
    private final HistorialDAO historialDAO;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel labelTotal;


    public HistorialPanel(SeñaController controller, MainFrame frame) {
        this.controller  = controller;
        this.frame       = frame;
        this.historialDAO = new HistorialDAO();
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        construirUI();
        cargarHistorial();
    }

    private void construirUI() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelTabla(),    BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel titulo = new JLabel("Historial de Traducciones");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(33, 37, 41));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setToolTipText("Recargar historial desde la base de datos");
        btnRefrescar.addActionListener(e -> cargarHistorial());

        JButton btnLimpiar = new JButton("Limpiar historial");
        btnLimpiar.setBackground(new Color(220, 53, 69));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.setToolTipText("Eliminar todo el historial de traducciones");
        btnLimpiar.addActionListener(e -> limpiarHistorial());

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnLimpiar);

        panel.add(titulo,        BorderLayout.WEST);
        panel.add(panelBotones,  BorderLayout.EAST);
        return panel;
    }

    private JScrollPane crearPanelTabla() {
        String[] columnas = {"#", "Texto traducido", "Señas", "Fecha y hora"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(new Color(230, 230, 230));
        tabla.setSelectionBackground(new Color(173, 216, 230));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(173, 216, 230));
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getAccessibleContext()
             .setAccessibleDescription("Tabla con el historial de traducciones realizadas");

        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        return scroll;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        labelTotal = new JLabel("Total en base de datos: 0 registro(s)");
        labelTotal.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        labelTotal.setForeground(Color.GRAY);

        panel.add(labelTotal);
        return panel;
    }

    
    public void cargarHistorial() {
        modeloTabla.setRowCount(0);

        // Historial de sesión actual
        List<EntradaHistorial> sesion = controller.obtenerHistorial();
        for (EntradaHistorial entrada : sesion) {
            modeloTabla.addRow(new Object[]{
                modeloTabla.getRowCount() + 1,
                entrada.getTextoOriginal(),
                entrada.getSeñas().size(),
                entrada.getFechaHora()
            });
        }

        // Historial persistido en MySQL
        List<String[]> bd = historialDAO.obtenerTodos();
        for (String[] fila : bd) {
            modeloTabla.addRow(new Object[]{
                fila[0],
                fila[1],
                fila[2],
                fila[3]
            });
        }

        int total = historialDAO.contarRegistros();
        labelTotal.setText("Total en base de datos: " + total + " registro(s)");
    }

    private void limpiarHistorial() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas eliminar todo el historial?\n"
            + "Esta acción no se puede deshacer.",
            "Confirmar limpieza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.limpiarHistorial();
            historialDAO.limpiar();
            cargarHistorial();
            JOptionPane.showMessageDialog(this,
                "Historial eliminado correctamente.",
                "Listo",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}