package cz.sio2.doclib;

import cz.sio2.doclib.processors.MicrosoftProcessor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    final List<FileProcessor> processorList = new ArrayList<>();

    {
        processorList.add(new MicrosoftProcessor());
    }

    public OverallResult process(final File file) {
        final OverallResult r = new OverallResult();
        _process(file, r);
        return r;
    }

    private void _process(final File file, final OverallResult result) {
//        System.out.println("Processing " + file);
        if ( file.isDirectory() ) {
            if ( file.listFiles() == null ) {
                return;
            }

            for(int i = 0; i < file.listFiles().length; i++) {
                _process(file.listFiles()[i], result);
            }
        } else {
            for( final FileProcessor p : processorList ) {
                if (p.accept(file)) {
                    ExplorationResult res = p.process(file);
                    result.addExplorationResult(
                            FilenameUtils
                                    .getExtension(file.getAbsolutePath()),res);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        final Engine e = new Engine();
        System.out.println(e.process(new File("/home/kremep1/fel/projects/15tacr-beta")));
    }
}
