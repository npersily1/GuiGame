public class Button {

    private int x, y, width, height;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(int mouseX, int mouseY) {

        return (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height);
    }
}
