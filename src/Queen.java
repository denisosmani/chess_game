import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Queen extends Piece {
    Queen(int x, int y,String color,Initialize initialize){
        super(x,y,color,initialize);
        Image queenImage;
        ImageView queenImageView;

        if(color.equals("black")) {
            queenImage = new Image("file:chessPieces/black_queen.png");
        }else{
            queenImage = new Image("file:chessPieces/white_queen.png");
        }
        queenImageView = new ImageView(queenImage);
        queenImageView.setFitWidth(60);
        queenImageView.setFitHeight(60);
        this.setTranslateX(x*60);
        this.setTranslateY(y*60);

        getChildren().add(queenImageView);

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
         //top
        for(int i=this.getY()-1; i>=0; i--){
            Initialize.squaresMatrix[this.getX()][i].setAttacked(true);
            if(Initialize.squaresMatrix[this.getX()][i].getOccupied()){
                break;
            }
        }

        //down
        for(int i=this.getY()+1; i<8; i++){
            Initialize.squaresMatrix[this.getX()][i].setAttacked(true);
            if(Initialize.squaresMatrix[this.getX()][i].getOccupied()){
                break;
            }
        }

        //right
        for(int i=this.getX()+1; i<8; i++){
            Initialize.squaresMatrix[i][this.getY()].setAttacked(true);
            if(Initialize.squaresMatrix[i][this.getY()].getOccupied()){
                break;
            }
        }

        //left
        for(int i=this.getX()-1; i>=0; i--){
            Initialize.squaresMatrix[i][this.getY()].setAttacked(true);
            if(Initialize.squaresMatrix[i][this.getY()].getOccupied()){
                break;
            }
        }

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
