import org.junit.*;

public class ToolsTest {

    @Test
    public void emptyParams() {
        String[] empty = new String[0];
        String[] actualArray = Tools.split("", ' ');
        assertArrayEquals(empty, actualArray);
    }

    @Test
    public void nothingToSplit() {
        String test = "qwerty";
        String[] expected = {test};
        String[] actual = Tools.split(test, ' ');
        assertArrayEquals(expected, actual);
    }

    @Test
    public void splitCharAtEnd() {
        String[] expected = {"qwert"};
        String[] actual = Tools.split("qwerty", 'y');
        assertArrayEquals(expected, actual);
    }

    @Test
    public void splitCharAtStart() {
        String[] expected = {"werty"};
        String[] actual = Tools.split("qwerty", 'q');
        assertArrayEquals(expected, actual);
    }

    @Test
    public void twoToSplit() {
        String[] expected = {"qwe", "rty"};
        String[] actual = Tools.split("qwe;rty", ';');
        assertArrayEquals(expected, actual);
    }

    @Test
    public void twoSplitCharsAfterEachOther() {
        String[] expected = {"qwe", "rty"};
        String[] actual = Tools.split("qwe;;rty", ';');
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeSplitCharsAfterEachOther() {
        String[] expected = {"qwe", "rty"};
        String[] actual = Tools.split("qwe;;;rty", ';');
        assertArrayEquals(expected, actual);
    }

    @Test
    public void manySplitChars() {
        String[] expected = {"qw", "e", "rtya", "sdf"};
        String[] actual = Tools.split(";qw;;;e;rtya;sdf;", ';');
        assertArrayEquals(expected, actual);
    }
}