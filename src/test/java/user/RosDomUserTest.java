package user;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosDomUserTest {

    final String fakePerfilPath = "src/test/java/resources/userPage1.txt";

    @Test
    public void readNameFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).name(),
                is("Humpelstilzchen")
        );
    }


}
