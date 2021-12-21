package mines;


import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.io.File;


import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ButtonHandler {
	private int h,w,numMines;
	private Mines myGame ;
	private Button[][] buttonTable;
	private Boolean gameEnded;

    @FXML
    private Button b1;
    
    @FXML
    private TextField width;

    @FXML
    private TextField height;

    @FXML
    private TextField mines;

    @FXML
    private Pane board;

    @FXML
    void resetAll(ActionEvent event) {
    	int i,j;
    	board.getChildren().clear();
    	GridPane g=new GridPane();
    	w=Integer.parseInt(width.getText());
    	h=Integer.parseInt(height.getText());
    	numMines=Integer.parseInt(mines.getText());
    	myGame=new Mines(h,w,numMines);
    	buttonTable=new Button[h][w];
    	gameEnded=false;
    	for( i=0;i<h;i++)
    		for(j=0;j<w;j++) {
    			buttonTable[i][j]=new Button(".");
    			buttonTable[i][j].setMinHeight(40);
    			buttonTable[i][j].setMinWidth(40);
    			buttonTable[i][j].setFont(Font.font(18));
    			g.add(buttonTable[i][j], j, i);
    			g.setPadding(new Insets(10));
    			g.setAlignment(Pos.CENTER);
    			buttonTable[i][j].setOnMouseClicked(new	mouseClick(i,j));
    		}

    	
    	
    	myGame.setShowAll(false);
    	board.getChildren().add(g);
    	board.getScene().getWindow().sizeToScene();
    }
    
	  class mouseClick implements EventHandler<MouseEvent>		
		{
	    	private int i,j;
			public mouseClick(int i, int j) {
				this.i=i;
				this.j=j;
			}

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY) {
					if(gameEnded) return;
					if(myGame.open(i, j)==false )	{
						File f;
						Stage popWindow = new Stage();
						StackPane s = new StackPane();
						Scene scn = new Scene(s);
						Label l; 
					    Background b = new Background(new BackgroundFill(Color.RED, null, null));	
					    buttonTable[i][j].setBackground(b);	
						f = new File("src/minesImages/image2.jpg");
						popWindow.setTitle("Loser!");
						gameEnded=false;
						cleanF();		
						myGame.setShowAll(true);		
						Image img = new Image(f.toURI().toString());
						ImageView imgV = new ImageView(img);		
						imgV.setFitWidth(200);
						imgV.setFitHeight(200);	
						l = new Label("", imgV);
						s.getChildren().add(l);
						popWindow.setScene(scn);
						popWindow.show();
					}
					if(myGame.isDone()) {
						File f;
						Stage popWindow = new Stage();
						StackPane s = new StackPane();
						Scene scn = new Scene(s);
						Label l;
						f = new File("src/minesImages/image1.jpg");
						popWindow.setTitle("Winner!");
						gameEnded=false;
						cleanF();		
						myGame.setShowAll(true);		
						Image img = new Image(f.toURI().toString());
						ImageView imgV = new ImageView(img);		
						imgV.setFitWidth(200);
						imgV.setFitHeight(200);	
						l = new Label("", imgV);
						s.getChildren().add(l);
						popWindow.setScene(scn);
						popWindow.show();
						
					}
				}
				else if(event.getButton() == MouseButton.SECONDARY ) {
					if(gameEnded) return;
					File f = new File("src/minesImages/Image4.jpg");
					double buttonW=buttonTable[i][j].getWidth()*0.4;
					double buttonH=buttonTable[i][j].getHeight()*0.4;
					Image img = new Image(f.toURI().toString(), buttonW, buttonH, false, true, true);
					myGame.toggleFlag(i, j);
					if(myGame.get(i,j).equals("F")) buttonTable[i][j].setGraphic(new ImageView(img));	
					else buttonTable[i][j].setGraphic(null);		
				}
				updateBoard();
			}
	  
		public void updateBoard()
		{
			File f = new File("src/minesImages/Image3.jpg");
			File f1 = new File("src/minesImages/Image4.jpg");
			double buttonW=buttonTable[i][j].getWidth()*0.4;
			double buttonH=buttonTable[i][j].getHeight()*0.4;
			Image img = new Image(f.toURI().toString(),buttonW,buttonH, false, true, true);
			Image img1 = new Image(f1.toURI().toString(),buttonW,buttonH, false, true, true);
			 cleanF();
			for(int i=0;i <h;i++)
				for(int j=0;j<w;j++){
					buttonTable[i][j].setAlignment(Pos.CENTER);
						buttonTable[i][j].setText(myGame.get(i,j));
					switch(myGame.get(i,j)) {
						case "1": {
							buttonTable[i][j].setGraphic(null);
							buttonTable[i][j].setTextFill(Color.BLUE);
							break;
							}
						case "2": {
							buttonTable[i][j].setGraphic(null);
							buttonTable[i][j].setTextFill(Color.GREEN);
							break;
						}
						case "X":{
							buttonTable[i][j].setGraphic(new ImageView(img));		
							buttonTable[i][j].setText(null);
							break;
						}
						case "F":{
							buttonTable[i][j].setGraphic(new ImageView(img1));
							buttonTable[i][j].setText(null);
							break;
						}
						case ".":{
							buttonTable[i][j].setFont(new Font(18));
							break;
						}
						default: {
							buttonTable[i][j].setGraphic(null);
							buttonTable[i][j].setTextFill(Color.RED);
							break;
						}
					}
				}
			
		}
	  	
	}
	public void cleanF(){
		for(int i=0;i<h;i++)
			for(int j=0;j<w;j++)
				if(gameEnded)		
					buttonTable[i][j].setGraphic(null);
	}		

}