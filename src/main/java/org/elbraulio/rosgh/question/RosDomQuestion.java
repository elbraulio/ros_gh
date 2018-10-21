package org.elbraulio.rosgh.question;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface RosDomQuestion {
    List<Answer> participants();

    int votes();
}
