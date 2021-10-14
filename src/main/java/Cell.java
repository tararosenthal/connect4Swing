import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {

    public Cell(Board board, String label) {
        this.setName(label);
        this.setText(" ");
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setFocusPainted(false);
        this.addActionListener(actionEvent ->
        {if(!board.isGameOver()) {
            String element;
            if(this.getText().isBlank()) {
                element = board.getElementIfValidMoveOrElseBlank(this);
                if (!element.isBlank()) {
                    this.setText(element);
                    board.checkStatus(this);
                }
            }
        }});
    }

    public void resetCell() {
        this.setText(" ");
    }
}
