package lab5;

import java.util.Comparator;


public class ComparadorUsuarios implements Comparator<Usuario> {

    @Override
    public int compare(Usuario o1, Usuario o2) {
        return o1.getId() - o2.getId();
        // ser� positivo, se o primeiro for maior;
        // ser� zero, se o primeiro for igual ao segundo;
        // ser� negativo, se for menor.
    }
}
