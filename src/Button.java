public class Button {
// Noah Persily GuiGame
    private int x, y, width, height;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Returns true if the mouse is inside the area of the button
    public boolean isClicked(int mouseX, int mouseY) {
        return ((mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height));
    }
}
