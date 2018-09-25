package mastermind;

import java.util.Random;
import javax.swing.*;

public class Mastermind extends javax.swing.JFrame {
    
    //Standart Variables that are used in the program.
    String[] colors = {"rood", "groen", "blauw", "geel", "oranje", "lichtblauw"};
    int[] selectedColors = new int[4];
    int[] pin = {-1,-1,-1,-1};
    int winCondition = 0; 
    int blackpins = 0;
    int whitepins = 0;
    int rows = 0;

    //creating all the labels we will use to bind images to
    JLabel solutionPins[] = new JLabel[4];
    JLabel historyPins[][] = new JLabel[7][4];
    JLabel checkPins[][] = new JLabel[7][4];
    JLabel endLabel = new JLabel();
    
    //the four labels you will actualy be able to change
    JLabel selectionPins1 = new JLabel();
    JLabel selectionPins2 = new JLabel();
    JLabel selectionPins3 = new JLabel();
    JLabel selectionPins4 = new JLabel();
    
    //the button that makes it possible to check for the solution
    JButton checkSolutionButton = new JButton("Check");
    
    /*
    **The generated class that comes wit a jFrame. 
    **We use this to generate the solution at the start of running the program
    **and to add the jLabels we created earlier.
    */
    public Mastermind() {       
        initComponents();
        
        //generate the solution the player has to solve
        generateSolution();
        
        //add te labels to the form and give them images.
        createPlayingField();
    }

    @SuppressWarnings("unchecked")
    //generated code from netBeans pretty empty for us. we created it ourself.
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    **Here we create the playing field we give the labels
    **icons and we give them their position
    */
    public void createPlayingField() {
        //we use these variables to change the values fast. mostly this was used
        //for testing purposes but its quite convinient.
        int hoogte = 50;
        int breedte = 50;
        int spacing = 10;

        //create the solutionPins where the correct colors are presented
        //and of course we make them invisible until the player wins or loses
        //but for positioning its better to create them now.
        for (int i = 0; i < 4; i++) {
            solutionPins[i] = new JLabel();
            solutionPins[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/" + colors[selectedColors[i]] + ".png")));
            solutionPins[i].setBounds((70) + hoogte * i, 0, hoogte, breedte);
            solutionPins[i].setVisible(false);
            this.add(solutionPins[i]);
        }
        
        //here we give the labels that we use to check the anwser the empty image.
        selectionPins1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
        selectionPins2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
        selectionPins3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
        selectionPins4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
                
        //we set the bounds for each of them 
        //mostly a lot of number crushing but the gist of it is that we give the a position relative to a starting position and
        //later we change their placement. hence we use a variable in the Bounds. it could also not be here but for
        //consistency's sake i left it in.
        selectionPins1.setBounds((70) + hoogte * 0, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
        selectionPins2.setBounds((70) + hoogte * 1, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
        selectionPins3.setBounds((70) + hoogte * 2, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
        selectionPins4.setBounds((70) + hoogte * 3, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
        
        //we actualy add the pins we use to check tot the JFrame. Yeah!!!!
        this.add(selectionPins1);
        this.add(selectionPins2);
        this.add(selectionPins3);
        this.add(selectionPins4);
        
        //here we add an eventlistener for every selectionPins so we can see when the
        //player clicks on the label.
        selectionPins1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //sends us to the method we are going to do the work of the check.
                selectionPins1Clicked(evt);
            }
        });
        selectionPins2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectionPins2Clicked(evt);
            }
        });
        selectionPins3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectionPins3Clicked(evt);
            }
        });
        selectionPins4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectionPins4Clicked(evt);
            }
        });

        //create all pins the we use on a later date to add the color the player selected.
        //we use a double for loop for 2 reasons. first of all les code to write. :P
        //second because we need 7 rows of 4 this way the can easely can be created without problems
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 4; i++) {
                historyPins[j][i] = new JLabel();
                //we give them the empty image to make them visible but giving them a color could confuse the player.
                historyPins[j][i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
                historyPins[j][i].setBounds((70) + hoogte * i, 55 + (breedte * j) + (j * spacing), hoogte, breedte);
                this.add(historyPins[j][i]);
            }
        }
        
        //we give the button the values it needs. once again. blablabla read the part about the solutionpins again same thing.
        checkSolutionButton.setBounds((-10) + hoogte * 0, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
        checkSolutionButton.setVisible(false);
        //setsize is only used for the button
        checkSolutionButton.setSize(70,50);
        this.add(checkSolutionButton);
        
        //once again add a mouselistener and add an event.
        checkSolutionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkPlayerSolution(evt);
            }
        });
        
        //whe change the variables values because for the next we need a diffrent value.
        hoogte = 25;
        breedte = 25;
        spacing = 25;
        
        //create the checkPins where the hints for the player show up.
        //als we use a double For loop because we need to do seven rows of four
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                checkPins[i][j] = new JLabel();
                checkPins[i][j].setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/PinPoint.png")));
                //if 2 pinpoints have been set then change the height so that it will be a square like real mastermind.
                if (j < 2) {
                    checkPins[i][j].setBounds(10 + (hoogte * j), 55 + (hoogte * i) + (i * spacing) + (i * 10), hoogte, breedte);
                } else {
                    checkPins[i][j].setBounds(10 + hoogte * (j - 2), 55 + (hoogte * i) + ((i + 1) * spacing) + (i * 10), hoogte, breedte);
                }
                this.add(checkPins[i][j]);
            }
        }
        
        //add a label where the text for winning or losing comes
        endLabel = new JLabel();
        endLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/Helaas.png")));
        endLabel.setBounds(hoogte * 0, 173 + (breedte * (6 - rows)) + ((6 - rows) * spacing), 275, 29);
        endLabel.setVisible(false);
        this.add(endLabel);
        
        endLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textClicked(evt);
            }
        });
    }
    
    /*
    **if clicked on the textLabel start new game.
    */
    public void textClicked(java.awt.event.MouseEvent evt){
        System.out.println("test");
        resetVariables();
        rows = 0;
        new Mastermind().setVisible(true);
        this.dispose();
    }
    
    /*
    **the method for selectionPins1 that cycles through colors.
    */
    public void selectionPins1Clicked(java.awt.event.MouseEvent evt){
        //if the pin is zero so at the start then start at first color.
        if(pin[0] == -1){
            pin[0] = 0;
            selectionPins1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[0]]+".png"))); // NOI18N
        }
        //if pin is higher or 5 start at first color again
        else if(pin[0] >= 5){
            pin[0] = 0;
            selectionPins1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[0]]+".png"))); // NOI18N
   
        }
        //otherwise go further one color
        else{
            pin[0]++;
            selectionPins1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[0]]+".png"))); // NOI18N
        }
        showCheckButton(checkSolutionButton);
    }
    
    /*
    **the method for selectionPins2 that cycles through colors.
    */
    public void selectionPins2Clicked(java.awt.event.MouseEvent evt){
        if(pin[1] == -1){
            pin[1] = 0;
            selectionPins2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[1]]+".png"))); // NOI18N
        }
        //if pin is higher or 5 start at first color again
        else if(pin[1] >= 5){
            pin[1] = 0;
            selectionPins2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[1]]+".png"))); // NOI18N
   
        }
        //otherwise go further one color
        else{
            pin[1]++;
            selectionPins2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[1]]+".png"))); // NOI18N
        }  
        showCheckButton(checkSolutionButton);
    }
    
    /*
    **the method for selectionPins3 that cycles through colors.
    */
    public void selectionPins3Clicked(java.awt.event.MouseEvent evt){
        //if the pin is zero so at the start then start at first color.
         if(pin[2] == -1){
            pin[2] = 0;
            selectionPins3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[2]]+".png"))); // NOI18N
        }
        //if pin is higher or 5 start at first color again
        else if(pin[2] >= 5){
            pin[2] = 0;
            selectionPins3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[2]]+".png"))); // NOI18N
   
        }
        //otherwise go further one color
        else{
            pin[2]++;
            selectionPins3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[2]]+".png"))); // NOI18N
        } 
        showCheckButton(checkSolutionButton);
    }
    
    /*
    **the method for selectionPins4 that cycles through colors.
    */
    public void selectionPins4Clicked(java.awt.event.MouseEvent evt){
        //if the pin is zero so at the start then start at first color.
         if(pin[3] == -1){
            pin[3] = 0;
            selectionPins4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[3]]+".png"))); // NOI18N
        }
        //if pin is higher or 5 start at first color again
        else if(pin[3] >= 5){
            pin[3] = 0;
            selectionPins4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[3]]+".png"))); // NOI18N
   
        }
        //otherwise go further one color
        else{
            pin[3]++;
            selectionPins4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/"+colors[pin[3]]+".png"))); // NOI18N
        } 
        showCheckButton(checkSolutionButton);
    }
    
    /*
    **check if the checksolutionbutton is allowed to show up.
    */
    public void showCheckButton(JButton checkSolutionButton){    
        //long if statement that says if nothing is -1 anymore then show the button.
        if(pin[0] != -1 && pin[1] != -1 && pin[2] != -1 && pin[3] != -1){
            checkSolutionButton.setVisible(true);
        }
    }
    
    /*
    **check which colors are correct and if the player gets a black pin or a white pin or ofcourse no pin at all.
    */
    public void checkPlayerSolution(java.awt.event.MouseEvent evt){
    
        //clone the selectedColors array because we need to change some values.
        //but we need to keep the values for later checks.
        //if we clone the array then it wont change the array we cloned if we change somthing in the new array.
        int[] tempSelectedColors = selectedColors.clone();
        //go to the colorpins statement
        colorPins();

        //first we check for the blackpins so that we can take those out of the tempSelectedColors so the cant accedentaly give us a 
        //whitepin as well. speaking from experiance here.... you dont want that.
        for(int i = 0; i <= 3; i++){
            //if both are the same then set that tempSelectedColors to -2 and also the pin to -1
            if(tempSelectedColors[i] == pin[i]){
            tempSelectedColors[i] = -2;
            pin[i] = -1;
            
            //then add a blackpin and add to the winCondition otherwise the player cant win the game.
            blackpins++;
            winCondition++;
            }      
        }
        //now we check for the whitepins for that we need 2 for loops
        //the first is the for the color the of the pin we need to guess
        //and with j we cycle through the chosen colors.
        for(int i = 0; i <= 3; i++){
            for(int j =0; j<= 3; j++){
            //if tempSelectedColors is the same as the pins the -2 and -1
            if(tempSelectedColors[i] == pin[j]){
                tempSelectedColors[i] = -2;
                pin[j] = -1;
                
                //add a whitepin.
                whitepins++;
                }
            }   
        }
        checkSolutionButton.setVisible(false);
        
        //if all four are correct you win
        if(winCondition == 4){
            endLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/Gefeliciteerd.png")));
            endLabel.setVisible(true);
            
            placeAnwserPins();
            finishGame();
        }
        //otherwise we go to placepins
        else{
            placeAnwserPins();
        }
    }
    
    /*
    **here we color the pins that our checkpins overlap at that moment
    */
    public void colorPins(){
        //we need to do this for 4 pins so thats wy we need the for loop
        for(int i = 0; i < 4; i++){
            //here the variable rows comes in to play with this we change the color of the correct rows.
            //so as example rows = 4 so it is 6 - 4 = 2 we change the icon of the 2 row.
            historyPins[(6 - rows)][i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/" + colors[pin[i]] + ".png")));
        }
    }
    
    /**
     **Very simple statement here we reset al the variables for the next round. 
     */
    public void resetVariables(){
        //change pin[i] all back to -1
        for(int i = 0; i < 4; i++){
            pin[i] = -1;
        }
        //set al these back to zero.
        blackpins = 0;
        whitepins = 0;
        winCondition = 0;
    }
    
    /*
    **Here we place the anwserpins
    **depending if the need to be black or white
    */
    public void placeAnwserPins(){
        //blackpins for loop is very simple we use the same way of making sure the rows is correct.
        for(int i = 0;i<blackpins; i++){
            checkPins[(6 - rows)][i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/zwart.png")));
        }
        //this one we add blackpins number to the starting value and we go throug as long as the combined number of white and black is not met
        //the reason we do this is so that we dont start at the first anwserpin again but at the on we left of.
        for(int j = 0 + blackpins; j<(whitepins+blackpins);j++){
            checkPins[(6 - rows)][j].setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/wit.png")));
        }
        
        //add rows because this row is done and we will go the the next very fast.
        rows++;
        //reset the variables.
        resetVariables();
        
        //if whe have seven rows and the player has not guessed then the player loses
        if(rows == 7){
            endLabel.setVisible(true);
            
            finishGame();
        }
        //otherwise we change the position of the buttons and selectionPins
        else{
            int hoogte = 50;
            int breedte = 50;
            int spacing = 10;

            //whit the variable we go tho the next row that we need to be on.
            checkSolutionButton.setBounds((-10) + hoogte * 0, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
            checkSolutionButton.setSize(70,50);

            //change the position of the pins we have a mouselistener on.
            selectionPins1.setBounds((70) + hoogte * 0, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
            selectionPins2.setBounds((70) + hoogte * 1, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
            selectionPins3.setBounds((70) + hoogte * 2, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);
            selectionPins4.setBounds((70) + hoogte * 3, 55 + (breedte * (6 - rows)) + ((6 - rows) * spacing), hoogte, breedte);

            //set the icon back to the start.
            selectionPins1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
            selectionPins2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
            selectionPins3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
            selectionPins4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mastermind/images/empty.png")));
        }
    }
    
    public void finishGame(){
        for(int i = 0; i < 4; i++){
            solutionPins[i].setVisible(true);
        }
        selectionPins1.setVisible(false);
        selectionPins2.setVisible(false);
        selectionPins3.setVisible(false);
        selectionPins4.setVisible(false);
    }
        
    /*
    **Here we generate the solution of the game the player has to guess
    */
    public void generateSolution() {
        //using the random function so we get a random number between 0 and 6
        //these numbers corespond to the colors that we use.
        Random random = new Random();
        for (int i = 0; i <= 3; i++) {
            int randomInt = random.nextInt(6);
            selectedColors[i] = randomInt;
        }
    }
    
    //almost useless main method. we just dont need it because we dont use static
    //methods or varaibles. becouse jFrame cant handle their awsomeness.
    //but it starts the mastermind frame so its cool.
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mastermind().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
