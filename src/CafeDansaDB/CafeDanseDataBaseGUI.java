package CafeDansaDB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class CafeDanseDataBaseGUI extends javax.swing.JFrame 
{
    private final String DANCER_TEXT_FILE = "src/CafeDansaDB/dancers.txt";
    /** Different styles of dance employed by the dancers. */
    public final static String[] STYLES = new String[]{"baladi", "balkan",
        "ballet", "ballroom", "bhangra", "firedancing", "irish step dancing", 
        "milonga", "modern pop", "rave", "salsa", "samba", "shuffle", "tap"};
    /** Different levels of proficiency held by the dancers. */
    public final static String[] PROFICIENCIES = new String[]{"beginner",
        "intermediate", "advanced", "expert", "master", "guru"};

    private ArrayList<Dancer> Dancers = new ArrayList<Dancer>();
    private Dancer infoDancer = new Dancer();
    private int currentID = 1;
   
    public CafeDanseDataBaseGUI() 
    {
        setResizable(false);
        initComponents();
        this.getRootPane().setDefaultButton(addJButton);
        //centers the form at start
        setLocationRelativeTo(null);
        //read from text file and fill ArrayList     
        readFromTextFile(DANCER_TEXT_FILE);
        //create DB
        createDB();
       //show first dancer
        Dancer myDancer = searchDancer(1);
        display(myDancer);
        displayDancer();
//        int index = DansajList.getSelectedIndex();
//        if(index >= 0)
//            display(index);
    }

    private void readFromTextFile(String textFile)
    {        
        try
        {            
            FileReader freader = new FileReader(textFile);
            BufferedReader breader = new BufferedReader(freader);
            // Read the first line
            String line = breader.readLine();
            
            while(line != null)
            {
                Dancer tempDancer = new Dancer();
                StringTokenizer token = new StringTokenizer(line, ",");
                while(token.hasMoreElements())
                {
                    tempDancer.setID(Integer.parseInt(token.nextToken()));
                    tempDancer.setDancerName(token.nextToken());
                    tempDancer.setStyle(token.nextToken());
                    tempDancer.setProf(token.nextToken());
                    tempDancer.setYears(Integer.parseInt(token.nextToken()));
                    tempDancer.setPhone(token.nextToken());
                    tempDancer.setEmail(token.nextToken());
                }
                
                // Add the dancer to the arrayList
                Dancers.add(tempDancer);
                
                // Read the next line
                line = breader.readLine();
            }
            // Close the BufferedReader
            breader.close();
        }
        catch(FileNotFoundException exp)
        {
            // Show error message
            JOptionPane.showMessageDialog(null, "Input error -- File not found.",
                    "Input Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception exp)
        {
            // Show error message
            JOptionPane.showMessageDialog(null, "Input error -- File could not be read.",
                    "Input Error!", JOptionPane.ERROR_MESSAGE);
        }
    }    
    /**************************************************************************
     * <pre>
     * Method       createDB()
     * Description  Make connection, drop existing table, and create database table
     * Date:        4/23/2019
     * @author      Niko Culevski
     * </pre>
     *************************************************************************/  
    private void createDB(){
        try{
            String url = "jdbc:mysql://localhost/dancers";
            String user = "root";
            String password = "Ldjcxldj1211!!";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            
            Statement stmt = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet table;
            
            table = dbm.getTables(null,null,"dancer",null);
            if(table.next()){
                stmt.executeUpdate("DROP TABLE dancer");
            }
            stmt.executeUpdate("CREATE TABLE dancer (dancerID INTEGER, DancerName"
                    + " VARCHAR(20), DanceStyle VARCHAR(20), Proficiency VARCHAR(15), "
                    + "Years INTEGER, Phone VARCHAR(20), Email VARCHAR(30))");
            for(int i = 0; i < Dancers.size(); i++){
                stmt.executeUpdate("INSERT INTO dancer VALUES("
                        + Dancers.get(i).getID() + ","
                        + "'" + Dancers.get(i).getName() + "',"
                        + "'" + Dancers.get(i).getStyle() + "'," 
                        + "'" + Dancers.get(i).getProf() + "',"
                        + Dancers.get(i).getYears() + ","
                        + "'" + Dancers.get(i).getPhone() + "',"
                        + "'" + Dancers.get(i).getEmail() + "')");
            }
            stmt.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "SQL Error","SQL ERROR!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**************************************************************************
     * <pre>
     * Method       searchEmployee()
     * Description  Search for employee with given indes
     * Date:        4/23/2019
     * @author      Niko Culevski
     * @param       id
     * @return      Employee
     * </pre>
     *************************************************************************/
   
    private Dancer searchDancer(int id){
        try{
            String url = "jdbc:mysql://localhost:3306/dancers";
            String user = "root";
            String password = "Ldjcxldj1211!!";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            
            infoDancer = new Dancer();
            
            String query = "SELECT * FROM dancer WHERE dancerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet results = pstmt.executeQuery();
            results.next();
            infoDancer.setID((results.getInt(1)));
            infoDancer.setDancerName(results.getString(2));
            infoDancer.setStyle(results.getString(3));
            infoDancer.setProf(results.getString(4));
            infoDancer.setYears(results.getInt(5));
            infoDancer.setPhone(results.getString(6));
            infoDancer.setEmail(results.getString(7));
            results.close();
            pstmt.close();
            conn.close();
            return infoDancer;
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error searching database for dancer", 
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return new Dancer();
        }
    }
    
    private void display(Dancer infomationDancer)
    {
        numberJLabel.setText(String.valueOf(infomationDancer.getID()));
        dancernameJTextField.setText(infomationDancer.getName());
        styleJTextField.setText(infomationDancer.getStyle());
        levelTextField.setText(infomationDancer.getProf());
        yearsJTextField.setText(String.valueOf(infomationDancer.getYears()));
        phoneJTextField.setText(infomationDancer.getPhone());
        emailJTextField.setText(infomationDancer.getEmail());
    }
    
    private void displayDancer() 
    {
//        int location = DansajList.getSelectedIndex();
//        String[] dancerArray = new String[Dancers.size()];
//        DansajList.setListData(dancerArray);
//        if(location < 0)
//            DansajList.setSelectedIndex(0);
//        else
//            DansajList.setSelectedIndex(location);
       
        try
        {
            String url = "jdbc:mysql://localhost:3306/dancers";
            String user = "root";
            String password = "Ldjcxldj1211!!";
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CafeDanseDataBaseGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM dancer";
            ResultSet rs = stmt.executeQuery(query);
            
            DefaultListModel dm = new DefaultListModel();
            
            while(rs.next())
            {
                String DancerNameDB = rs.getString("DancerName");
                dm.addElement(DancerNameDB);
                DansajList.setModel(dm);
            }
        }
        catch (SQLException ex) 
            {
                Logger.getLogger(CafeDanseDataBaseGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        displayJPanel = new javax.swing.JPanel();
        pic1JLabel = new javax.swing.JLabel();
        addJButton = new javax.swing.JButton();
        DansajList = new javax.swing.JList<>();
        editjButton = new javax.swing.JButton();
        DeletejButton = new javax.swing.JButton();
        exitjButton = new javax.swing.JButton();
        titleJPanel = new javax.swing.JPanel();
        pic2JLabel = new javax.swing.JLabel();
        titleJLabel = new javax.swing.JLabel();
        informationJPanel = new javax.swing.JPanel();
        dancernameJTextField = new javax.swing.JTextField();
        styleJTextField = new javax.swing.JTextField();
        levelTextField = new javax.swing.JTextField();
        yearsJTextField = new javax.swing.JTextField();
        phoneJTextField = new javax.swing.JTextField();
        emailJTextField = new javax.swing.JTextField();
        namedancerJLabel = new javax.swing.JLabel();
        styleJLabel = new javax.swing.JLabel();
        phoneJLabel = new javax.swing.JLabel();
        yearsJLabel = new javax.swing.JLabel();
        emailJLabel = new javax.swing.JLabel();
        levelJLabel = new javax.swing.JLabel();
        numberJLabel = new javax.swing.JLabel();
        lastJButton = new javax.swing.JButton();
        nextJButton = new javax.swing.JButton();
        previousJButton = new javax.swing.JButton();
        firstJButton = new javax.swing.JButton();
        fileJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        clearJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        sortJMenu = new javax.swing.JMenu();
        nameJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        yearJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        actionJMenu = new javax.swing.JMenu();
        addJMenuItem = new javax.swing.JMenuItem();
        deleteJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dansa DataBase");
        setResizable(false);

        addJButton.setText("ADD");
        addJButton.setToolTipText("Add new ballet");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });

        DansajList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                DansajListValueChanged(evt);
            }
        });

        javax.swing.GroupLayout displayJPanelLayout = new javax.swing.GroupLayout(displayJPanel);
        displayJPanel.setLayout(displayJPanelLayout);
        displayJPanelLayout.setHorizontalGroup(
            displayJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DansajList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(displayJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addComponent(pic1JLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        displayJPanelLayout.setVerticalGroup(
            displayJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayJPanelLayout.createSequentialGroup()
                .addComponent(pic1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DansajList, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        editjButton.setText("Edit");
        editjButton.setToolTipText("Edit ballet. Press Enter in any of the JTextFields to confirm changes...");
        editjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editjButtonActionPerformed(evt);
            }
        });

        DeletejButton.setText("Delete");
        DeletejButton.setToolTipText("Delete ballet");
        DeletejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletejButtonActionPerformed(evt);
            }
        });

        exitjButton.setText("Exit");
        exitjButton.setToolTipText("Exit application");
        exitjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitjButtonActionPerformed(evt);
            }
        });

        titleJLabel.setFont(new java.awt.Font("SimSun", 1, 24)); // NOI18N
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("Cafe Dansa DataBase");

        javax.swing.GroupLayout titleJPanelLayout = new javax.swing.GroupLayout(titleJPanel);
        titleJPanel.setLayout(titleJPanelLayout);
        titleJPanelLayout.setHorizontalGroup(
            titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleJPanelLayout.createSequentialGroup()
                .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pic2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        titleJPanelLayout.setVerticalGroup(
            titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleJPanelLayout.createSequentialGroup()
                .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pic2JLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dancernameJTextField.setEditable(false);
        dancernameJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        styleJTextField.setEditable(false);
        styleJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        levelTextField.setEditable(false);
        levelTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        yearsJTextField.setEditable(false);
        yearsJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        phoneJTextField.setEditable(false);
        phoneJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        emailJTextField.setEditable(false);
        emailJTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        namedancerJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        namedancerJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        namedancerJLabel.setText("Name of dancer:");

        styleJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        styleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        styleJLabel.setText("Dancy style:");

        phoneJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        phoneJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        phoneJLabel.setText("Phone:");

        yearsJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        yearsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        yearsJLabel.setText("Years dancing:");

        emailJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        emailJLabel.setText("Email:");

        levelJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        levelJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        levelJLabel.setText("Level of proficiency:");

        numberJLabel.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N

        lastJButton.setBackground(new java.awt.Color(204, 255, 204));
        lastJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lastJButton.setMnemonic('L');
        lastJButton.setText(">>");
        lastJButton.setToolTipText("Last");
        lastJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastJButtonActionPerformed(evt);
            }
        });

        nextJButton.setBackground(new java.awt.Color(204, 255, 204));
        nextJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nextJButton.setMnemonic('N');
        nextJButton.setText(">");
        nextJButton.setToolTipText("Next");
        nextJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextJButtonActionPerformed(evt);
            }
        });

        previousJButton.setBackground(new java.awt.Color(204, 255, 204));
        previousJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        previousJButton.setMnemonic('r');
        previousJButton.setText("<");
        previousJButton.setToolTipText("Previous");
        previousJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousJButtonActionPerformed(evt);
            }
        });

        firstJButton.setBackground(new java.awt.Color(204, 255, 204));
        firstJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        firstJButton.setMnemonic('s');
        firstJButton.setText("<<");
        firstJButton.setToolTipText("First");
        firstJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout informationJPanelLayout = new javax.swing.GroupLayout(informationJPanel);
        informationJPanel.setLayout(informationJPanelLayout);
        informationJPanelLayout.setHorizontalGroup(
            informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationJPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationJPanelLayout.createSequentialGroup()
                        .addComponent(levelJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationJPanelLayout.createSequentialGroup()
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(yearsJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearsJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationJPanelLayout.createSequentialGroup()
                        .addComponent(styleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(styleJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationJPanelLayout.createSequentialGroup()
                        .addComponent(namedancerJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dancernameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationJPanelLayout.createSequentialGroup()
                        .addComponent(numberJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationJPanelLayout.createSequentialGroup()
                        .addComponent(firstJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(previousJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastJButton)
                        .addGap(8, 8, 8))))
        );
        informationJPanelLayout.setVerticalGroup(
            informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationJPanelLayout.createSequentialGroup()
                .addComponent(numberJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dancernameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namedancerJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(styleJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(styleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(levelJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearsJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearsJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastJButton)
                    .addComponent(nextJButton)
                    .addComponent(previousJButton)
                    .addComponent(firstJButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fileJMenu.setText("File");

        clearJMenuItem.setMnemonic('C');
        clearJMenuItem.setText("Clear");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(clearJMenuItem);

        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print Form");
        printJMenuItem.setToolTipText("Print form as a GUI");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        exitJMenuItem.setMnemonic('E');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        fileJMenuBar.add(fileJMenu);

        sortJMenu.setMnemonic('N');
        sortJMenu.setText("Sort");

        nameJRadioButtonMenuItem.setMnemonic('n');
        nameJRadioButtonMenuItem.setSelected(true);
        nameJRadioButtonMenuItem.setText("By Name");
        nameJRadioButtonMenuItem.setToolTipText("Sort by name and display only name");
        nameJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(nameJRadioButtonMenuItem);

        yearJRadioButtonMenuItem.setMnemonic('Y');
        yearJRadioButtonMenuItem.setText("By Year");
        yearJRadioButtonMenuItem.setToolTipText("Sort by year and display name and year");
        yearJRadioButtonMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yearJRadioButtonMenuItemStateChanged(evt);
            }
        });
        yearJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(yearJRadioButtonMenuItem);

        fileJMenuBar.add(sortJMenu);

        actionJMenu.setText("Action");

        addJMenuItem.setText("Add New Dancer");
        addJMenuItem.setToolTipText("Add new Ballet");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(addJMenuItem);

        deleteJMenuItem.setText("Delete Dancer");
        deleteJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(deleteJMenuItem);

        editJMenuItem.setText("Edit Dancer");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(editJMenuItem);

        searchJMenuItem.setText("Search");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(searchJMenuItem);

        fileJMenuBar.add(actionJMenu);

        helpJMenu.setText("Help");

        aboutJMenuItem.setText("About");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        fileJMenuBar.add(helpJMenu);

        setJMenuBar(fileJMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(displayJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(exitjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(titleJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(informationJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(informationJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
////        // Call clearData method
////        clearData();
    }//GEN-LAST:event_clearJMenuItemActionPerformed

    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        // Print the form as a GUI
        PrintUtilities.printComponent(this);
    }//GEN-LAST:event_printJMenuItemActionPerformed

    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        // End program
        System.exit(0);
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    private void nameJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameJRadioButtonMenuItemActionPerformed
//        // call the displayCities() method
//        displayBallets();
    }//GEN-LAST:event_nameJRadioButtonMenuItemActionPerformed

    private void yearJRadioButtonMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_yearJRadioButtonMenuItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_yearJRadioButtonMenuItemStateChanged

    private void yearJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearJRadioButtonMenuItemActionPerformed
//        // call the displayCities() method
//        displayBallets();
    }//GEN-LAST:event_yearJRadioButtonMenuItemActionPerformed

    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
//        //show the About form
//        About myAbout = new About(this, true);
//        myAbout.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
//        //Search database
//        String balletName = JOptionPane.showInputDialog("Enter name of ballet");
//        int index = searchByName(balletName);
//        if(index < 0)
//        {
//            JOptionPane.showMessageDialog(null, "City " + balletName
//                + " Not in the database ", "Search City",
//                JOptionPane.INFORMATION_MESSAGE);
//        }
//        else
//        {
//            DansajList.setSelectedIndex(index);
//
//        }
    }//GEN-LAST:event_searchJMenuItemActionPerformed

    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        //Same feature as editjButton
        editjButton.doClick();    
    }//GEN-LAST:event_editJMenuItemActionPerformed

    private void deleteJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJMenuItemActionPerformed
        //Same feature as DeletejButton
        DeletejButton.doClick();   
    }//GEN-LAST:event_deleteJMenuItemActionPerformed

    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        //Same feature as addJButton
        addJButton.doClick();    
    }//GEN-LAST:event_addJMenuItemActionPerformed

    private void DansajListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_DansajListValueChanged
//        // Display the data for the selected ballet from Jlist
//        int index = DansajList.getSelectedIndex();
//        if(index >= 0)
//        display(index);
//        //selectBallet();
    }//GEN-LAST:event_DansajListValueChanged

    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
        String message = "Dancer not added";
        try{
            AddDancer myAddForm = new AddDancer(this, true);
            myAddForm.setVisible(true);
            int lastIndex = 0;
            Dancer newDansa = myAddForm.getDancer();
            if(newDansa != null && !exists(newDansa)){
                String url = "jdbc:mysql://localhost/dancers";
                String user = "root";
                String password = "Ldjcxldj1211!!";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM dancer";
                
                ResultSet rs = stmt.executeQuery(query);
                rs.last();
                
                lastIndex = rs.getInt("DancerID");
                newDansa.setID(lastIndex + 1);
                stmt.executeUpdate("INSERT INTO dancer VALUES("
                        + newDansa.getID() + ","
                        + "'" + newDansa.getName() + "',"
                        + "'" + newDansa.getStyle() + "'," 
                        + "'" + newDansa.getProf() + "',"
                        + newDansa.getYears() + ","
                        + "'" + newDansa.getPhone() + "',"
                        + "'" + newDansa.getEmail() + "')");
                Dancers.add(newDansa);
                display(newDansa);
                conn.close();
            }
            else{
                message = "Dancer not added";
                newDansa.setID(lastIndex);
                display(newDansa);
                throw new NullPointerException();
            }
        }catch(NullPointerException exp){
            JOptionPane.showMessageDialog(null, message,
                    "Input Error!", JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, message,
                "DataBase Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addJButtonActionPerformed

    private void editjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editjButtonActionPerformed
        int index = this.DansajList.getSelectedIndex();
        String message = "Employee not edited";
        try{
            currentID = Integer.parseInt(numberJLabel.getText());
            infoDancer = searchDancer(currentID);
            
            EditDancer myEditForm = new EditDancer(infoDancer);
            myEditForm.setVisible(true);
            int lastIndex = 0;
            Dancer editDansa = myEditForm.getDancer();
            editDansa.setID(currentID);
            if(editDansa != null && !exists(editDansa)){
                infoDancer.setID(currentID);
                infoDancer.setDancerName(editDansa.getName());
                infoDancer.setStyle(editDansa.getStyle());
                infoDancer.setProf(editDansa.getProf());
                infoDancer.setYears(editDansa.getYears());
                infoDancer.setPhone(editDansa.getPhone());
                infoDancer.setEmail(editDansa.getEmail());
                String url = "jdbc:mysql://localhost/dancers";
                String user = "root";
                String password = "Ldjcxldj1211!!";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                String query = "UPDATE dancer SET DancerName = '" + infoDancer.getName() + "', "
                        + "DanceStyle = '" + infoDancer.getStyle() + "', "
                        + "Proficiency = '" + infoDancer.getProf() + "', "
                        + "Years = " + infoDancer.getYears() + ", "
                        + "Phone = '" + infoDancer.getPhone() + "', "
                        + "Email = '" + infoDancer.getEmail() + "' "
                        + "WHERE DancerID = " + infoDancer.getID();
                stmt.executeUpdate(query);
                display(editDansa);
                stmt.close();
                conn.close();
            }
            else{
                message = "Employee not added";
                editDansa.setID(lastIndex);
                display(editDansa);
                throw new NullPointerException();
            }
        }catch(NullPointerException exp){
            JOptionPane.showMessageDialog(null, message,
                    "Input Error!", JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, message,
                "DataBase Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_editjButtonActionPerformed

    private void DeletejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletejButtonActionPerformed
        int index = Integer.parseInt(numberJLabel.getText());
        Dancer toDeleteDansa = searchDancer(index);
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
                + toDeleteDansa.getName() + 
                "?", "Delete Dancer", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION)
        {
            try
            {
                String url = "jdbc:mysql://localhost/dancers";
                String user = "root";
                String password = "Ldjcxldj1211!!";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                
                String query = "DELETE FROM dancer WHERE DancerID = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1,index);
                pstmt.execute();
                
                //saveFile();
                //sizeOfDB--;
                query = "SELECT * FROM dancer";
                ResultSet rs = stmt.executeQuery(query);
                rs.next(); //move to first record
                index = rs.getInt("DancerID");
                display(searchDancer(index));
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error Deleteing!",
                    "Delete Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_DeletejButtonActionPerformed

    private void exitjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitjButtonActionPerformed
        //End program
        System.exit(0);
    }//GEN-LAST:event_exitjButtonActionPerformed

    private void lastJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastJButtonActionPerformed
        currentID = Dancers.size();
        Dancer myEmployee = searchDancer(currentID);
        display(myEmployee);
    }//GEN-LAST:event_lastJButtonActionPerformed

    private void nextJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextJButtonActionPerformed
        currentID = ++currentID  % (Dancers.size() + 1);
        if(currentID == 0)
        currentID = 1;
        Dancer myEmployee = searchDancer(currentID);
        display(myEmployee);
    }//GEN-LAST:event_nextJButtonActionPerformed

    private void previousJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousJButtonActionPerformed
        currentID = --currentID  % Dancers.size();
        if(currentID == 0)
        currentID = Dancers.size();
        Dancer myEmployee = searchDancer(currentID);
        display(myEmployee);
    }//GEN-LAST:event_previousJButtonActionPerformed

    private void firstJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstJButtonActionPerformed
        currentID = 1;
        Dancer myDancer = searchDancer(currentID);
        display(myDancer);
    }//GEN-LAST:event_firstJButtonActionPerformed

    public boolean exists(Dancer dansa){
        boolean found = false;
        try{
            String url = "jdbc:mysql://localhost/dancers";
            String user = "root";
            String password = "Ldjcxldj1211!!";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM dancer";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next() && !found){
                int id = rs.getInt("DancerID");
                infoDancer = searchDancer(id);
                if(dansa.equals(infoDancer))
                    found = true;
            }
            conn.close();
        }catch(Exception exp){
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in exists",
                "DataBase Error!", JOptionPane.ERROR_MESSAGE);
        }
        return found;
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CafeDanseDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CafeDanseDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CafeDanseDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CafeDanseDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CafeDanseDataBaseGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> DansajList;
    private javax.swing.JButton DeletejButton;
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JMenu actionJMenu;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JTextField dancernameJTextField;
    private javax.swing.JMenuItem deleteJMenuItem;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JButton editjButton;
    private javax.swing.JLabel emailJLabel;
    private javax.swing.JTextField emailJTextField;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JButton exitjButton;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenuBar fileJMenuBar;
    private javax.swing.JButton firstJButton;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JPanel informationJPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton lastJButton;
    private javax.swing.JLabel levelJLabel;
    private javax.swing.JTextField levelTextField;
    private javax.swing.JRadioButtonMenuItem nameJRadioButtonMenuItem;
    private javax.swing.JLabel namedancerJLabel;
    private javax.swing.JButton nextJButton;
    private javax.swing.JLabel numberJLabel;
    private javax.swing.JLabel phoneJLabel;
    private javax.swing.JTextField phoneJTextField;
    private javax.swing.JLabel pic1JLabel;
    private javax.swing.JLabel pic2JLabel;
    private javax.swing.JButton previousJButton;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JMenu sortJMenu;
    private javax.swing.JLabel styleJLabel;
    private javax.swing.JTextField styleJTextField;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel;
    private javax.swing.JRadioButtonMenuItem yearJRadioButtonMenuItem;
    private javax.swing.JLabel yearsJLabel;
    private javax.swing.JTextField yearsJTextField;
    // End of variables declaration//GEN-END:variables
}
