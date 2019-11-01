package Java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

public class Principal extends JFrame implements ActionListener {
    private JMenuBar barraMenu;
    private JMenu menuArchivo, menuAnalisis;
    // Menu Archivo
    private JMenuItem itemNuevo, itemAbrir, itemGuardar, itemSalir, itemParser, itemScanner,itemGuardarComo,itemSemantico,itemIntermedio;
    private JFileChooser ventanaArchivos;
    private File archivo;
    private JTextPane areaTexto, terminal,intermedio;
    private JTabbedPane documentos, consola;
    private String[] titulos = {"ID", "Tipo"};
    DefaultTableModel modelo = new DefaultTableModel(new Object[0][0], titulos);

    private JTable mitabla = new JTable(modelo);

    private Principal() {
        super("Compilador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());
        setLayout(new GridLayout(2, 0));
        setSize(600, 450);
        setLocationRelativeTo(null);
        creaInterFaz();
        setVisible(true);
    }

    private void creaInterFaz() {
        barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);
        menuArchivo = new JMenu("Archivo");
        menuArchivo.setIcon(new ImageIcon("archivo.png"));
        menuAnalisis = new JMenu("Fases");
        menuAnalisis.setIcon(new ImageIcon("analisis.png"));
        ventanaArchivos = new JFileChooser();
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir...");
        itemGuardar = new JMenuItem("Guardar...");
        itemSalir = new JMenuItem("Salir");
        itemGuardarComo = new JMenuItem("Guardar como...");
        itemSalir.addActionListener(this);
        itemGuardar.addActionListener(this);
        itemAbrir.addActionListener(this);
        itemNuevo.addActionListener(this);
        itemScanner = new JMenuItem("Scanner");
        itemParser = new JMenuItem("Parser");
        itemParser.addActionListener(this);
        itemScanner.addActionListener(this);
        itemGuardarComo.addActionListener(this);
        itemSemantico = new JMenuItem("Semantico");
        itemSemantico.addActionListener(this);
        itemIntermedio = new JMenuItem("Bytecode");
        itemIntermedio.addActionListener(this);
        ventanaArchivos = new JFileChooser();
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemGuardarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        menuAnalisis.add(itemScanner);
        menuAnalisis.add(itemParser);
        menuAnalisis.add(itemSemantico);
        menuAnalisis.add(itemIntermedio);
        barraMenu.add(menuArchivo);
        barraMenu.add(menuAnalisis);
        areaTexto = new JTextPane();
        TextLineNumber tln = new TextLineNumber(areaTexto);
        ventanaArchivos = new JFileChooser("Guardar");
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 12));
        documentos = new JTabbedPane();
        consola = new JTabbedPane();
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setRowHeaderView(tln);
        documentos.addTab("Nuevo", scroll);
        areaTexto.setDocument(new Documento());
        documentos.setToolTipText("Aqui se muestra el codigo");
        add(documentos);
        terminal = new JTextPane();
        terminal.setEditable(false);
        intermedio = new JTextPane();
        intermedio.setEditable(false);
        consola.addTab("Consola", new JScrollPane(terminal));
        consola.addTab("Tabla", new JScrollPane(mitabla));
        consola.addTab("Bytecode",new JScrollPane(intermedio));
        intermedio.setFont(new Font("monospaced", Font.PLAIN, 12));
        add(consola);
        itemNuevo.setIcon(new ImageIcon("nuevo.png"));
        itemGuardar.setIcon(new ImageIcon("guardar.png"));
        itemGuardarComo.setIcon(new ImageIcon("guardar.png"));
        itemAbrir.setIcon(new ImageIcon("abrir.png"));
        itemSalir.setIcon(new ImageIcon("salir.png"));
        itemScanner.setIcon(new ImageIcon("scanner.png"));
        itemParser.setIcon(new ImageIcon("parser.png"));
        itemSemantico.setIcon(new ImageIcon("semantico.png"));
        itemIntermedio.setIcon(new ImageIcon("java.png"));
        documentos.setIconAt(0, new ImageIcon("codigo.png"));
        consola.setIconAt(0, new ImageIcon("consola.png"));
        consola.setIconAt(1, new ImageIcon("tabla.png"));
        consola.setIconAt(2, new ImageIcon("java.png"));
        consola.setToolTipText("Aqui se muestra el resultado del analisis");
    }

    private void appendToPane(JTextPane tp, String msg, Color c) throws BadLocationException {
        StyledDocument doc = tp.getStyledDocument();
        Style style = tp.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, c);
        doc.insertString(doc.getLength(), msg, style);

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Principal();
    }

    public boolean guardar(boolean flag) {
        try {
            if (archivo == null||flag) {
                ventanaArchivos.setDialogTitle("Guardando..");
                ventanaArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (ventanaArchivos.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
                    return false;
                archivo = ventanaArchivos.getSelectedFile();
                documentos.setTitleAt(0, archivo.getName());
            }
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(areaTexto.getText());
            bf.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Houston tenemos un problema?");
            return false;
        }
        return true;
    }

    public boolean abrir() {
        String texto = "", linea;
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null)
                texto += linea + "\n";
            areaTexto.setText(texto);
            return true;
        } catch (Exception e) {
            archivo = null;
            JOptionPane.showMessageDialog(null, "Tipo de archivo incompatible", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == itemNuevo){
            archivo = null;
            terminal.setText("");
            areaTexto.setText("");
            modelo = new DefaultTableModel(new Object[0][0], titulos);
            mitabla.setModel(modelo);
        }
        if(e.getSource() == itemGuardarComo){
            guardar(true);
            return;
        }
        if (e.getSource() == itemSalir) {
            System.exit(0);
            return;
        }
        if (e.getSource() == itemAbrir) {
            ventanaArchivos.setDialogTitle("Abrir..");
            ventanaArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (ventanaArchivos.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
                return;
            archivo = ventanaArchivos.getSelectedFile();
            documentos.setTitleAt(0, archivo.getName());
            abrir();
            terminal.setText("");
            intermedio.setText("");
            modelo = new DefaultTableModel(new Object[0][0], titulos);
            mitabla.setModel(modelo);
        }
        if (e.getSource() == itemGuardar) {
            guardar(false);
            return;
        }
        if (e.getSource() == itemParser || e.getSource() == itemScanner|| e.getSource()== itemSemantico||e.getSource() == itemIntermedio) {
            terminal.setText("");
            intermedio.setText("");
            modelo = new DefaultTableModel(new Object[0][0], titulos);
            mitabla.setModel(modelo);
            if(!guardar(false))
                return;
            Scanner analisis = new Scanner(archivo.getAbsolutePath());
            analisis.analizar();
            Parser parser = new Parser(analisis);
            for (String salida : analisis.dameSalidas()) {
                if (salida.charAt(salida.length() - 1) == '$') {
                    try {
                        appendToPane(terminal, salida.substring(0, salida.length() - 1) + "\n", Color.RED);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, salida.substring(0, salida.length() - 1));
                    return;
                }
                try {
                    appendToPane(terminal, salida + "\n", Color.BLACK);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                appendToPane(terminal, "El Scanning no tuvo errores \n", Color.GREEN);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            if (e.getSource() == itemScanner)
                return;
            parser.program();
            for (String salida :(ArrayList<String>)  parser.dameSalida()) {
                if (salida.charAt(salida.length() - 1) == '$') {
                    try {
                        appendToPane(terminal, salida.substring(0, salida.length() - 1) + "\n", Color.RED);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, salida.substring(0, salida.length() - 1));
                    return;
                }
                try {
                    appendToPane(terminal, salida + "\n", Color.BLACK);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                appendToPane(terminal, "El Parsing no tuvo errores \n", Color.GREEN);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            if(e.getSource()==itemSemantico || e.getSource()==itemIntermedio){
                for (String salida :(ArrayList<String>)  parser.dameSemantico()) {
                    if (salida.charAt(salida.length() - 1) == '$') {
                        try {
                            appendToPane(terminal, salida.substring(0, salida.length() - 1) + "\n", Color.RED);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, salida.substring(0, salida.length() - 1));
                        return;
                    }
                    try {
                        appendToPane(terminal, salida + "\n", Color.BLACK);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    appendToPane(terminal, "El semantico no tuvo errores \n", Color.GREEN);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                for (Map.Entry<String, String> var : parser.tablaSimbolos.entrySet()){
                    modelo.addRow(new String []{var.getKey(), var.getValue()});
                }
            }
            if(itemIntermedio==e.getSource()) {
                String fina = "";
                for (ByteLine linea : parser.byteCode) {
                    fina += linea.toString() + "\n";
                }
                intermedio.setText(fina);
                JOptionPane.showMessageDialog(null, "Se genero codigo intermedio");
            }
        }


    }
}
