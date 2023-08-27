import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Piece {
    Pawn(int x, int y, String color, Initialize initialize) {
        super(x, y, color, initialize);
        Image pawnImage;
        ImageView pawnImageView;

        if (color.equals("black")) {
            pawnImage = new Image("file:chessPieces/black_pawn.png");
        } else {
            pawnImage = new Image("file:chessPieces/white_pawn.png");
        }

        pawnImageView = new ImageView(pawnImage);
        pawnImageView.setFitWidth(60);
        pawnImageView.setFitHeight(60);
        this.setTranslateX(x * 60);
        this.setTranslateY(y * 60);

        getChildren().add(pawnImageView);

    }

    @Override
    protected boolean Move(int x2, int y2) {
        clearAttacks();

        if (this.getColor().equals("white")) {
            if (y2 == this.getY() - 2 && this.getX() == x2 && this.getNumberOfMoves() == 0 && !Initialize.squaresMatrix[x2][y2].getOccupied() && !Initialize.squaresMatrix[x2][y2 + 1].getOccupied()) {
                return true;
            } else if (y2 == this.getY() - 1 && this.getX() == x2 && !Initialize.squaresMatrix[x2][y2].getOccupied()) {
                return true;
            }else { //Capture
                clearAttacks();
                this.Attack();
                if(Initialize.squaresMatrix[x2][y2].getAttacked() && Initialize.squaresMatrix[x2][y2].getOccupied()){
                    return true;
                }else {
                    return false;
                }
            }


        } else {
            //pawn is black
            if (y2 == this.getY() + 2 && this.getX() == x2 && this.getNumberOfMoves() == 0 && !Initialize.squaresMatrix[x2][y2].getOccupied() && !Initialize.squaresMatrix[x2][y2 - 1].getOccupied()) {
                return true;
            } else if (y2 == this.getY() + 1 && this.getX() == x2 && !Initialize.squaresMatrix[x2][y2].getOccupied()) {
                return true;
            }else {
                clearAttacks();
                this.Attack();
                if(Initialize.squaresMatrix[x2][y2].getAttacked() && Initialize.squaresMatrix[x2][y2].getOccupied()){
                    return true;
                }else {
                    return false;
                }
            }
        }

    }

    @Override
    protected void Attack() {
        if (this.getColor().equals("white")) {
            if (this.getY() != 0) {
                if (this.getX() != 0 && this.getX() != 7) {
                    Initialize.squaresMatrix[this.getX() + 1][this.getY() - 1].setAttacked(true);//top-right
                    Initialize.squaresMatrix[this.getX() - 1][this.getY() - 1].setAttacked(true);//top-left

                } else if (this.getX() == 0) {
                    Initialize.squaresMatrix[this.getX() + 1][this.getY() - 1].setAttacked(true);//top-right
                } else {
                    Initialize.squaresMatrix[this.getX() - 1][this.getY() - 1].setAttacked(true);//top-left
                }
            }
        } else {//pawn is black
            if (this.getY() != 7) {
                if (this.getX() != 0 && this.getX() != 7) {
                    Initialize.squaresMatrix[this.getX() + 1][this.getY() + 1].setAttacked(true);//top-left
                    Initialize.squaresMatrix[this.getX() - 1][this.getY() + 1].setAttacked(true);//top-right

                } else if (this.getX() == 0) {
                    Initialize.squaresMatrix[this.getX() + 1][this.getY() + 1].setAttacked(true);//top-left
                } else {
                    Initialize.squaresMatrix[this.getX() - 1][this.getY() + 1].setAttacked(true);//top-right
                }
            }
        }
    }
}