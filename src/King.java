import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class King extends Piece {
    King(int x, int y,String color, Initialize initialize){
        super(x,y,color,initialize);
        Image kingImage;
        ImageView kingImageView;

        if(color.equals("black")) {
            kingImage = new Image("file:chessPieces/black_king.png");
        }else{
            kingImage = new Image("file:chessPieces/white_king.png");
        }
        kingImageView = new ImageView(kingImage);
        kingImageView.setFitWidth(60);
        kingImageView.setFitHeight(60);
        this.setTranslateX(x*60);
        this.setTranslateY(y*60);

        getChildren().add(kingImageView);

    }
    @Override
    protected boolean  Move(int x2, int y2){

        int c1 = Initialize.shortRooks.get(0).getNumberOfMoves();
        int c2 = Initialize.shortRooks.get(1).getNumberOfMoves();
        int c3 = Initialize.longRooks.get(0).getNumberOfMoves();
        int c4 = Initialize.longRooks.get(1).getNumberOfMoves();

        int c5 = Initialize.twoKings.get(0).getNumberOfMoves();
        int c6 = Initialize.twoKings.get(1).getNumberOfMoves();

        clearAttacks();
        this.Attack();
        if( Initialize.squaresMatrix[x2][y2].getAttacked() ){

            clearAttacks();
            allCounterAttacks(this.getColor());

            if(Initialize.squaresMatrix[x2][y2].getAttacked()){
                return false;
            }else{
                return true;
            }
        }else {
            if ((this.getColor().equals("white") && c5 == 0) || (this.getColor().equals("black") && c6 == 0)) {

                Square rightSquare = Initialize.squaresMatrix[this.getX() + 1][this.getY()];
                Square rightSquare1 = Initialize.squaresMatrix[this.getX() + 2][this.getY()]; //out of bounds

                Square leftSquare = Initialize.squaresMatrix[this.getX() - 1][this.getY()];
                Square leftSquare1 = Initialize.squaresMatrix[this.getX() - 2][this.getY()]; //out of bounds
                clearAttacks();
                allCounterAttacks(this.getColor());
                if (
                        (this.getY() == y2 && this.getX() + 2 == x2 && this.getColor().equals("white") && c1 == 0 && c5 == 0 && !rightSquare.getAttacked() && !rightSquare1.getAttacked() && !rightSquare.getOccupied() && !rightSquare1.getOccupied()) ||
                                (this.getY() == y2 && this.getX() + 2 == x2 && this.getColor().equals("black") && c2 == 0 && c6 == 0 && !rightSquare.getAttacked() && !rightSquare1.getAttacked() && !rightSquare.getOccupied() && !rightSquare1.getOccupied()) ||
                                (this.getY() == y2 && this.getX() - 2 == x2 && this.getColor().equals("white") && c3 == 0 && c5 == 0 && !leftSquare.getAttacked() && !leftSquare1.getAttacked() && !leftSquare.getOccupied() && !leftSquare1.getOccupied()) ||
                                (this.getY() == y2 && this.getX() - 2 == x2 && this.getColor().equals("black") && c4 == 0 && c6 == 0 && !leftSquare.getAttacked() && !leftSquare1.getAttacked() && !leftSquare.getOccupied() && !leftSquare1.getOccupied())
                ) {
                    //short-castle
                    if (this.getY() == y2 && this.getX() + 2 == x2) {
                        shortCastle();
                    }
                    //long-castle
                    if (this.getY() == y2 && this.getX() - 2 == x2) {
                        longCastle();
                    }
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    @Override
    protected void Attack() {
        if(this.getX() != 0 && this.getX() != 7 && this.getY() != 0 && this.getY() != 7){
            //top
            Initialize.squaresMatrix[this.getX()][this.getY()-1].setAttacked(true);
            //top-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()-1].setAttacked(true);
            //top-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()-1].setAttacked(true);
            //right
            Initialize.squaresMatrix[this.getX()+1][this.getY()].setAttacked(true);
            //left
            Initialize.squaresMatrix[this.getX()-1][this.getY()].setAttacked(true);
            //down
            Initialize.squaresMatrix[this.getX()][this.getY()+1].setAttacked(true);
            //down-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()+1].setAttacked(true);
            //down-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()+1].setAttacked(true);
        }else if(this.getX() == 7 && this.getY() != 0 && this.getY() !=7){ //right-side of board
            //top
            Initialize.squaresMatrix[this.getX()][this.getY()-1].setAttacked(true);
            //top-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()-1].setAttacked(true);
            //left
            Initialize.squaresMatrix[this.getX()-1][this.getY()].setAttacked(true);
            //down-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()+1].setAttacked(true);
            //down
            Initialize.squaresMatrix[this.getX()][this.getY()+1].setAttacked(true);
        }else if(this.getX() == 0 && this.getY() != 0 && this.getY() !=7){ //left-side of board
            //top
            Initialize.squaresMatrix[this.getX()][this.getY()-1].setAttacked(true);
            //top-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()-1].setAttacked(true);
            //right
            Initialize.squaresMatrix[this.getX()+1][this.getY()].setAttacked(true);
            //down-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()+1].setAttacked(true);
            //down
            Initialize.squaresMatrix[this.getX()][this.getY()+1].setAttacked(true);
        }else if(this.getY() == 0 && this.getX() != 0 && this.getX() != 7){ //top-side of board
            //right
            Initialize.squaresMatrix[this.getX()+1][this.getY()].setAttacked(true);
            //left
            Initialize.squaresMatrix[this.getX()-1][this.getY()].setAttacked(true);
            //down
            Initialize.squaresMatrix[this.getX()][this.getY()+1].setAttacked(true);
            //down-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()+1].setAttacked(true);
            //down-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()+1].setAttacked(true);
        }else if(this.getY() == 7 && this.getX() != 0 && this.getX() != 7){ //bottom-side of board
            //top
            Initialize.squaresMatrix[this.getX()][this.getY()-1].setAttacked(true);
            //top-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()-1].setAttacked(true);
            //top-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()-1].setAttacked(true);
            //right
            Initialize.squaresMatrix[this.getX()+1][this.getY()].setAttacked(true);
            //left
            Initialize.squaresMatrix[this.getX()-1][this.getY()].setAttacked(true);
        }else if(this.getX() == 0 && this.getY() == 0){ //top-left corner
            //right
            Initialize.squaresMatrix[this.getX()+1][this.getY()].setAttacked(true);
            //down
            Initialize.squaresMatrix[this.getX()][this.getY()+1].setAttacked(true);
            //down-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()+1].setAttacked(true);
        }else if(this.getX() == 0 && this.getY() ==7){//down-left corner
            //top
            Initialize.squaresMatrix[this.getX()][this.getY()-1].setAttacked(true);
            //top-right
            Initialize.squaresMatrix[this.getX()+1][this.getY()-1].setAttacked(true);
            //right
            Initialize.squaresMatrix[this.getX()+1][this.getY()].setAttacked(true);
        }else if(this.getX() == 7 && this.getY() == 7){ //down-right corner
            //top
            Initialize.squaresMatrix[this.getX()][this.getY()-1].setAttacked(true);
            //top-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()-1].setAttacked(true);
            //left
            Initialize.squaresMatrix[this.getX()-1][this.getY()].setAttacked(true);
        }else if(this.getX() == 7 && this.getY() == 0){ //top-right corner
            //left
            Initialize.squaresMatrix[this.getX()-1][this.getY()].setAttacked(true);
            //down
            Initialize.squaresMatrix[this.getX()][this.getY()+1].setAttacked(true);
            //down-left
            Initialize.squaresMatrix[this.getX()-1][this.getY()+1].setAttacked(true);
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

    private void shortCastle(){
        Piece whiteRightRook = Initialize.shortRooks.get(0);
        Piece blackRightRook = Initialize.shortRooks.get(1);

        if(this.getColor().equals("white")) {
                playCastleSound();
                Initialize.squaresMatrix[7][7].setOccupied(false);
                Initialize.squaresMatrix[7][7].setPieceOnSquare(null);

                whiteRightRook.setTranslateX(whiteRightRook.getTranslateX() - 120);
                whiteRightRook.setX(whiteRightRook.getX()-2);

                Initialize.squaresMatrix[5][7].setOccupied(true);
                Initialize.squaresMatrix[5][7].setPieceOnSquare(whiteRightRook);

        }else{//king is black
                playCastleSound();
                Initialize.squaresMatrix[7][0].setOccupied(false);
                Initialize.squaresMatrix[7][0].setPieceOnSquare(null);

                blackRightRook.setTranslateX(blackRightRook.getTranslateX() - 120);
                blackRightRook.setX(blackRightRook.getX()-2);

                Initialize.squaresMatrix[5][0].setOccupied(true);
                Initialize.squaresMatrix[5][0].setPieceOnSquare(blackRightRook);
        }
    }
    private void longCastle(){
        Piece whiteLeftRook = Initialize.longRooks.get(0);
        Piece blackLeftRook = Initialize.longRooks.get(1);

        if(this.getColor().equals("white")) {
                playCastleSound();
                Initialize.squaresMatrix[0][7].setOccupied(false);
                Initialize.squaresMatrix[0][7].setPieceOnSquare(null);

                whiteLeftRook.setTranslateX(whiteLeftRook.getTranslateX() + 180);
                whiteLeftRook.setX(whiteLeftRook.getX()+3);

                Initialize.squaresMatrix[3][7].setOccupied(true);
                Initialize.squaresMatrix[3][7].setPieceOnSquare(whiteLeftRook);

        }else{//king is black
                playCastleSound();
                Initialize.squaresMatrix[0][0].setOccupied(false);
                Initialize.squaresMatrix[0][0].setPieceOnSquare(null);

                blackLeftRook.setTranslateX(blackLeftRook.getTranslateX() + 180);
                blackLeftRook.setX(blackLeftRook.getX()+3);

                Initialize.squaresMatrix[3][0].setOccupied(true);
                Initialize.squaresMatrix[3][0].setPieceOnSquare(blackLeftRook);
        }
    }

    private MediaPlayer mediaPlayer;
    private void playCastleSound() {
        String audioFilePath = "soundEffects/castle.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


}
