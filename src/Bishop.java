import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bishop extends Piece {
    Bishop(int x, int y,String color,Initialize initialize){
        super(x,y,color,initialize);
        Image bishopImage;
        ImageView bishopImageView;

        if(color.equals("black")) {
            bishopImage = new Image("file:chessPieces/black_bishop.png");
        }else{
            bishopImage = new Image("file:chessPieces/white_bishop.png");
        }
        bishopImageView = new ImageView(bishopImage);
        bishopImageView.setFitWidth(60);
        bishopImageView.setFitHeight(60);
        this.setTranslateX(x*60);
        this.setTranslateY(y*60);

        getChildren().add(bishopImageView);

    }
    @Override
    protected boolean  Move(int x2, int y2){
        clearAttacks();
        this.Attack();
        if(Initialize.squaresMatrix[x2][y2].getAttacked()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void Attack() {
       //top-right
        if (this.getY() != 0 && this.getX() != 7) {
            for (int i = 0; i < 8; i++) {
                Initialize.squaresMatrix[this.getX() + i + 1][this.getY() - i - 1].setAttacked(true);
                if (this.getX() + i + 1 == 7 || this.getY() - i - 1 == 0 || Initialize.squaresMatrix[this.getX() + i + 1][this.getY() - i - 1].getOccupied()) {
                    break;
                }
            }
        }

        //down-left
        if(this.getX() !=0 && this.getY() != 7) {
            for (int i = 0; i < 8; i++) {
                Initialize.squaresMatrix[this.getX() - i - 1][this.getY() + i + 1].setAttacked(true);
                if (this.getX() - i - 1 == 0 || this.getY() + i + 1 == 7 || Initialize.squaresMatrix[this.getX() - i - 1][this.getY() + i + 1].getOccupied()) {
                    break;
                }
            }
        }


        //down-right
        if(this.getX() != 7 && this.getY() != 7) {
            for (int i = 0; i < 8; i++) {
                Initialize.squaresMatrix[this.getX() + i + 1][this.getY() + i + 1].setAttacked(true);
                if (this.getX() + i + 1 == 7 || this.getY() + i + 1 == 7 || Initialize.squaresMatrix[this.getX() + i + 1][this.getY() + i + 1].getOccupied()) {
                    break;
                }
            }
        }

        //top-left
        if(this.getX() != 0 && this.getY() != 0) {
            for (int i = 0; i < 8; i++) {
                Initialize.squaresMatrix[this.getX() - i - 1][this.getY() - i - 1].setAttacked(true);
                if (this.getX() - i - 1 == 0 || this.getY() - i - 1 == 0 || Initialize.squaresMatrix[this.getX() - i - 1][this.getY() - i - 1].getOccupied()) {
                    break;
                }
            }
        }

    }
}
