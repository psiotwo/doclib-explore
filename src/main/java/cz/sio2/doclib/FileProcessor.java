package cz.sio2.doclib;

import java.io.File;

public interface FileProcessor {

    boolean accept( final File file );

    ExplorationResult process( final File file );
}
