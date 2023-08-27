import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;

public abstract class Piece extends StackPane {
    private final Initialize initialize;
    private final String color;
    private int x;
    private int y;
    private int numberOfMoves;
    private boolean whiteKingInCheck;
    private boolean blackKingInCheck;
    public Piece(int x, int y, String color,Initialize initialize){

        this.color = color;
        this.x = x;
        this.y = y;
        this.initialize = initialize;
        Initialize.squaresMatrix[this.x][this.y].setPieceOnSquare(this);
        Initialize.squaresMatrix[this.x][this.y].setOccupied(true);

        this.numberOfMoves = 0;
        this.whiteKingInCheck = false;
        this.blackKingInCheck = false;

        setOnMouseClicked(this::handlePieceClick);
        setOnMousePressed(this::handlePiecePress);
        setOnMouseDragged(this::handlePieceDrag);
        setOnMouseEntered(this::handlePieceEnter);
        setOnMouseReleased(this::handlePieceRelease);
        setOnMouseExited(this::handlePieceExit);
    }

    private void handlePieceClick(MouseEvent event){
        //showpiece moves
    }

    private double offsetX;
    private double offsetY;
    private int prevX;
    private int prevY;
    private void handlePiecePress(MouseEvent event){
       // clearAttacks();
        setCursor(Cursor.CLOSED_HAND);
        offsetX =  event.getSceneX()-this.getTranslateX();
        offsetY =  event.getSceneY()-this.getTranslateY();
        prevX = (int) this.getTranslateX();
        prevY = (int) this.getTranslateY();
    }

    private void handlePieceDrag(MouseEvent event) {
        if (!this.color.equals(Initialize.prevColor)) { //could be put in release
            this.setTranslateX(event.getSceneX() - offsetX);
            this.setTranslateY(event.getSceneY() - offsetY);
        }
    }
    private Color saveOriginalColor;
    private int saveOriginalX;
    private int saveOriginalY;
    private void handlePieceEnter(MouseEvent event){
        Square targetSquare = Initialize.squaresMatrix[(int)((getTranslateX()/60))][(int)((getTranslateY())/60)];

        this.saveOriginalColor = targetSquare.getSquareColor();
        saveOriginalX = (int)((getTranslateX()/60));
        saveOriginalY = (int)((getTranslateY())/60);

        targetSquare.setSquareColor(Color.LIGHTGREEN);
        setCursor(Cursor.HAND);
    }

    private void handlePieceExit(MouseEvent event){
        Initialize.squaresMatrix[saveOriginalX][saveOriginalY].setSquareColor(saveOriginalColor);
    }
    private void handlePieceRelease(MouseEvent event) {
        setCursor(Cursor.DEFAULT);

        Square targetSquare = Initialize.squaresMatrix[((int) ((getTranslateX() + 30.0) / 60))][((int) ((getTranslateY() + 30.0) / 60))];
        Square prevSquare = Initialize.squaresMatrix[prevX / 60][prevY / 60];

        Piece savePiece = targetSquare.getPieceOnSquare();

        boolean whiteKingWasInCheck = Initialize.twoKings.get(0).getWhiteKingInCheck();
        boolean blackKingWasInCheck = Initialize.twoKings.get(1).getBlackKingInCheck();

            if (Move(targetSquare.getX(), targetSquare.getY())) {
                if (!targetSquare.getOccupied()) { // no piece on square
                    makeMove();
                    checkChecks();

                    // Check if the King's check status changed
                    boolean whiteKingInCheck = Initialize.twoKings.get(0).getWhiteKingInCheck();
                    boolean blackKingInCheck = Initialize.twoKings.get(1).getBlackKingInCheck();

                // If the King's check status changed, and they are still in check
                    if ((whiteKingWasInCheck && whiteKingInCheck) || (blackKingWasInCheck && blackKingInCheck) || (whiteKingInCheck && this.getColor().equals("black")) || (blackKingInCheck && this.getColor().equals("white"))) {
                        // Revert the move since it didn't get the King out of check
                        undoMove(prevSquare,targetSquare);
                    }
                    checkPromotion(this,targetSquare.getX(), targetSquare.getY());


                } else {//there is a piece on square
                    String currentPieceColor = targetSquare.getPieceOnSquare().getColor();
                    String prevPieceColor = prevSquare.getPieceOnSquare().getColor();

                    if (currentPieceColor.equals(prevPieceColor)) {//if there is a piece of the same color
                        noMove();
                    } else {//if there is a piece of different color
                        capture();
                        checkChecks();

                        // Check if the King's check status changed
                        boolean whiteKingInCheck = Initialize.twoKings.get(0).getWhiteKingInCheck();
                        boolean blackKingInCheck = Initialize.twoKings.get(1).getBlackKingInCheck();

                        // If the King's check status changed, and they are still in check
                        if ((whiteKingWasInCheck && whiteKingInCheck) || (blackKingWasInCheck && blackKingInCheck) || (whiteKingInCheck && this.getColor().equals("black") ) || (blackKingInCheck && this.getColor().equals("white"))) {
                            // Revert the move since it didn't get the King out of check
                            undoCapture(prevSquare, targetSquare,savePiece);
                        }
                        checkPromotion(this, targetSquare.getX(), targetSquare.getY());
                    }
                }
            } else {
                noMove();
            }
    }


    private MediaPlayer mediaPlayer;
    private void playMoveSound() {
        String audioFilePath = "soundEffects/move.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    private void playCaptureSound() {
        String audioFilePath = "soundEffects/capture.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setRate(1.1);
        mediaPlayer.play();
    }
    private void playCheckSound() {
        String audioFilePath = "soundEffects/check.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setRate(1.1);
        mediaPlayer.play();
    }

    public String getColor(){
        return this.color;
    }
    public int getNumberOfMoves(){
        return numberOfMoves;
    }
    public void setNumberOfMoves(int numberOfMoves){
        this.numberOfMoves = numberOfMoves;
    }
    public int getX(){return this.x;}
    public void setX(int x){this.x = x;}
    public int getY(){return this.y;}
    public void setY(int y){this.y = y;}
    public boolean getWhiteKingInCheck(){
        return Initialize.twoKings.get(0).whiteKingInCheck;
    }
    public void setWhiteKingInCheck(boolean whiteKingInCheck){
        Initialize.twoKings.get(0).whiteKingInCheck = whiteKingInCheck;
    }
    public boolean getBlackKingInCheck(){
        return Initialize.twoKings.get(1).blackKingInCheck;
    }
    public void setBlackKingInCheck(boolean blackKingInCheck){
        Initialize.twoKings.get(1).blackKingInCheck = blackKingInCheck;
    }
    protected abstract boolean Move(int x2, int y2);//x2 y2 square the player wants to put the piece on
    protected abstract void Attack();
    public void clearAttacks(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Initialize.squaresMatrix[i][j].setAttacked(false);
            }
        }
    }
    private void makeMove(){
        playMoveSound();

        this.setNumberOfMoves(this.getNumberOfMoves() + 1);
        Square targetSquare = Initialize.squaresMatrix[((int) ((getTranslateX() + 30.0) / 60))][((int) ((getTranslateY() + 30.0) / 60))];
        Square prevSquare = Initialize.squaresMatrix[prevX/60][prevY/60];

        targetSquare.setOccupied(true);
        targetSquare.setPieceOnSquare(this);

        setTranslateX((((int) ((getTranslateX() + 30.0) / 60)) * 60));//move happens
        setTranslateY((((int) ((getTranslateY() + 30.0) / 60)) * 60));

        this.setX(targetSquare.getX());
        this.setY(targetSquare.getY());
        Initialize.prevColor = this.getColor();

        prevSquare.setPieceOnSquare(null);
        prevSquare.setOccupied(false);

    }
    private void undoMove(Square prevSquare, Square targetSquare){

        this.setNumberOfMoves(this.getNumberOfMoves() - 1);

        targetSquare.setOccupied(false);
        targetSquare.setPieceOnSquare(null);

        setTranslateX(prevX);//move happens
        setTranslateY(prevY);

        this.setX(prevSquare.getX());
        this.setY(prevSquare.getY());

        prevSquare.setPieceOnSquare(this);
        prevSquare.setOccupied(true);

        if(Initialize.prevColor.equals("white")) {
            Initialize.prevColor = "black";
        }else{
            Initialize.prevColor ="white";
        }
    }


    private void capture(){
        playCaptureSound();
        this.setNumberOfMoves(this.getNumberOfMoves() + 1);

        Square targetSquare = Initialize.squaresMatrix[((int) ((getTranslateX() + 30.0) / 60))][((int) ((getTranslateY() + 30.0) / 60))];
        Square prevSquare = Initialize.squaresMatrix[prevX/60][prevY/60];

        initialize.remove(targetSquare.getPieceOnSquare());
        targetSquare.setPieceOnSquare(null);


        targetSquare.setOccupied(true);
        targetSquare.setPieceOnSquare(this);
        Initialize.prevColor = this.getColor();

        //the move happens
        setTranslateX((((int) ((getTranslateX() + 30.0) / 60)) * 60));
        setTranslateY((((int) ((getTranslateY() + 30.0) / 60)) * 60));

        this.setX(targetSquare.getX());
        this.setY(targetSquare.getY());

        prevSquare.setPieceOnSquare(null);
        prevSquare.setOccupied(false);
    }
    private void undoCapture(Square prevSquare, Square targetSquare, Piece capturedPiece){
        this.setNumberOfMoves(this.getNumberOfMoves() - 1);

        targetSquare.setOccupied(true);
        initialize.resetPiece(capturedPiece);
        targetSquare.setPieceOnSquare(capturedPiece);

        setTranslateX(prevX);// move happens
        setTranslateY(prevY);

        this.setX(prevSquare.getX());
        this.setY(prevSquare.getY());

        prevSquare.setPieceOnSquare(this);
        prevSquare.setOccupied(true);

        if (Initialize.prevColor.equals("white")) {
            Initialize.prevColor = "black";
        } else {
            Initialize.prevColor = "white";
        }
    }

    private void noMove(){
        setTranslateX(prevX); //the move doesn't happen
        setTranslateY(prevY);
    }

    private void checkChecks(){
        Square whiteKingSquare = Initialize.squaresMatrix[Initialize.twoKings.get(0).getX()][Initialize.twoKings.get(0).getY()];
        Square blackKingSquare = Initialize.squaresMatrix[Initialize.twoKings.get(1).getX()][Initialize.twoKings.get(1).getY()];

            clearAttacks();
            allCounterAttacks("white");

            if(whiteKingSquare.getAttacked()){
                playCheckSound();
                Initialize.twoKings.get(1).setBlackKingInCheck(true);
            }else{
                Initialize.twoKings.get(1).setBlackKingInCheck(false);
            }


            clearAttacks();
            allCounterAttacks("black");

            if(blackKingSquare.getAttacked()){
                playCheckSound();
                Initialize.twoKings.get(0).setWhiteKingInCheck(true);
            }else{
                Initialize.twoKings.get(0).setWhiteKingInCheck(false);
            }

    }
    private void checkPromotion(Piece piece, int x2, int y2){
        if(piece instanceof Pawn && piece.getColor().equals("white") && y2==0 ){
            Piece removeThis = Initialize.squaresMatrix[x2][y2].getPieceOnSquare();
            initialize.remove(removeThis);
            Queen newQueen = new Queen(x2,y2,"white",initialize);
            initialize.resetPiece(newQueen);
            newQueen.setX(x2);
            newQueen.setY(y2);
            Initialize.squaresMatrix[x2][y2].setPieceOnSquare(newQueen);
            Initialize.squaresMatrix[x2][y2].setOccupied(true);
            Initialize.allPieces.add(newQueen);
        }

        if(piece instanceof Pawn && piece.getColor().equals("black") && y2==7 ){
            Piece removeThis = Initialize.squaresMatrix[x2][y2].getPieceOnSquare();
            initialize.remove(removeThis);
            Queen newQueen = new Queen(x2,y2,"black",initialize);
            initialize.resetPiece(newQueen);
            newQueen.setX(x2);
            newQueen.setY(y2);
            Initialize.squaresMatrix[x2][y2].setPieceOnSquare(newQueen);
            Initialize.squaresMatrix[x2][y2].setOccupied(true);
            Initialize.allPieces.add(newQueen);
        }
    }

    private void allCounterAttacks(String colorOfKing){
        if(colorOfKing.equals("white")){
            for(int i=0; i<Initialize.allPieces.size(); i++){
                if(Initialize.allPieces.get(i).getColor().equals("black")){
                    Initialize.allPieces.get(i).Attack();
                }
            }
        }else{
            for(int i=0; i<Initialize.allPieces.size(); i++){
                if(Initialize.allPieces.get(i).getColor().equals("white")){
                    Initialize.allPieces.get(i).Attack();
                }
            }
        }
    }

}
