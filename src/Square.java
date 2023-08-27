import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

public class Square extends StackPane {
    private final Color color;
    private final Rectangle square;
    private final int x;
    private final int y;
    private boolean isOccupied;
    private boolean isAttacked;
    private Piece pieceOnSquare;
    public Square(int x, int y,Color color) {
        this.x=x;
        this.y=y;
        this.pieceOnSquare = null;
        this.color = color;
        this.square = new Rectangle(60, 60);

        this.square.setFill(this.color);
        setTranslateX(x*60);
        setTranslateY(y*60);
        this.isOccupied = false;

        getChildren().add(square);
        setOnMouseEntered(this::handleSquareEnter);
        setOnMouseExited(this::handleSquareExit);
        setOnMouseClicked(this::handleSquareClick);
    }

    public void setSquareColor(Color setColor){
        this.square.setFill(setColor);
    }
    public Color getSquareColor(){
        return this.color;
    }

    private Color saveOriginalColor;
    private void handleSquareEnter(MouseEvent event) {
        this.setSquareColor(Color.LIGHTGREEN);
        this.saveOriginalColor = this.color;
    }
    private void handleSquareClick(MouseEvent event){

    }
    private void handleSquareExit(MouseEvent event){
        this.setSquareColor(saveOriginalColor);
    }

    public void setOccupied(boolean occupied){
        this.isOccupied = occupied;
    }

    public boolean getOccupied(){return this.isOccupied;}

    public void setPieceOnSquare(Piece pieceOnSquare){
        this.pieceOnSquare = pieceOnSquare;
    }

    public Piece getPieceOnSquare(){ return this.pieceOnSquare; }
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public void setAttacked(boolean attacked){
        this.isAttacked = attacked;
    }

    public boolean getAttacked(){return this.isAttacked;}

}
