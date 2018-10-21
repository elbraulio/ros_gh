package testtools;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ResetDb {
    public void reset() throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("./src/test/resources/sqlite/createdb.sh");
        proc.waitFor();
    }
}
