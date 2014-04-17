package com.compomics.denovogui.cmd;

import com.compomics.denovogui.DeNovoSequencingHandler;
import com.compomics.denovogui.preferences.DenovoguiPathPreferences;
import com.compomics.software.CompomicsWrapper;
import com.compomics.util.experiment.biology.*;
import com.compomics.util.experiment.massspectrometry.SpectrumFactory;
import com.compomics.util.gui.waiting.waitinghandlers.WaitingHandlerCLIImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import org.apache.commons.cli.*;

/**
 * This class can be used to control DeNovoGUI in command line.
 *
 * @author Marc Vaudel
 * @author Harald Barsnes
 */
public class DeNovoCLI implements Callable {

    /**
     * The command line parameters.
     */
    private DeNovoCLIInputBean deNovoCLIInputBean;
    /**
     * The post translational modifications factory.
     */
    private PTMFactory ptmFactory = PTMFactory.getInstance();
    /**
     * The enzyme factory.
     */
    private EnzymeFactory enzymeFactory = EnzymeFactory.getInstance();

    /**
     * Construct a new SearchCLI runnable from a SearchCLI Bean. When
     * initialization is successful, calling "run" will start SearchGUI and
     * write the output files when finished.
     *
     * @param args the command line arguments
     */
    public DeNovoCLI(String[] args) {

        try {
            // Load modifications
            try {
                ptmFactory.importModifications(DeNovoSequencingHandler.getModificationsFile(getJarFilePath()), false);
            } catch (Exception e) {
                System.out.println("An error occurred while loading the modifications.");
                e.printStackTrace();
            }
            try {
                ptmFactory.importModifications(DeNovoSequencingHandler.getUserModificationsFile(getJarFilePath()), true);
            } catch (Exception e) {
                System.out.println("An error occurred while loading the user modifications.");
                e.printStackTrace();
            }
            try {
                enzymeFactory.importEnzymes(DeNovoSequencingHandler.getEnzymesFile(getJarFilePath()));
            } catch (Exception e) {
                System.out.println("An error occurred while loading the enzymes.");
                e.printStackTrace();
            }
            Options lOptions = new Options();
            DeNovoCLIParams.createOptionsCLI(lOptions);
            BasicParser parser = new BasicParser();
            CommandLine line = parser.parse(lOptions, args);

            if (!DeNovoCLIInputBean.isValidStartup(line)) {
                PrintWriter lPrintWriter = new PrintWriter(System.out);
                lPrintWriter.print("\n======================" + System.getProperty("line.separator"));
                lPrintWriter.print("DeNovoCLI" + System.getProperty("line.separator"));
                lPrintWriter.print("======================" + System.getProperty("line.separator"));
                lPrintWriter.print(getHeader());
                lPrintWriter.print(DeNovoCLIParams.getOptionsAsString());
                lPrintWriter.flush();
                lPrintWriter.close();

                System.exit(0);
            } else {
                deNovoCLIInputBean = new DeNovoCLIInputBean(line);
                call();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calling this method will run the configured DeNovoCLI process.
     */
    public Object call() {

        PathSettingsCLIInputBean pathSettingsCLIInputBean = deNovoCLIInputBean.getPathSettingsCLIInputBean();
        if (pathSettingsCLIInputBean.hasInput()) {
            PathSettingsCLI pathSettingsCLI = new PathSettingsCLI(pathSettingsCLIInputBean);
            pathSettingsCLI.setPathSettings();
        } else {
            try {
                setPathConfiguration();
            } catch (Exception e) {
                System.out.println("An error occured when setting path configuration. Default will be used.");
                e.printStackTrace();
            }
        }

        try {
            WaitingHandlerCLIImpl waitingHandlerCLIImpl = new WaitingHandlerCLIImpl();

            boolean runPepNovo = deNovoCLIInputBean.enablePepNovo();
            boolean runDirecTag = deNovoCLIInputBean.enableDirecTag();

            File pepNovoExecutable = deNovoCLIInputBean.getPepNovoExecutable();
            File direcTagExecutable = deNovoCLIInputBean.getDirecTagExecutable();
            File pepNovoFolder = null;
            File direcTagFolder = null;
            String pepNovoExecutableTitle = null;
            String direcTagExecutableTitle = null;

            if (pepNovoExecutable != null) {
                pepNovoExecutableTitle = pepNovoExecutable.getName();
                pepNovoFolder = pepNovoExecutable.getParentFile();
            } else if (new File(getJarFilePath() + "/resources/PepNovo").exists()) {

                // use the default PepNovo folder if not set by user
                pepNovoFolder = new File(getJarFilePath() + "/resources/PepNovo");

                // OS check
                String osName = System.getProperty("os.name").toLowerCase();

                if (osName.contains("mac os")) {
                    pepNovoExecutableTitle = "PepNovo_Mac";
                } else if (osName.contains("windows")) {
                    pepNovoExecutableTitle = "PepNovo_Windows.exe";
                } else if (osName.indexOf("nix") != -1 || osName.indexOf("nux") != -1) {
                    pepNovoExecutableTitle = "PepNovo_Linux";
                } else {
                    // unsupported OS version
                }
            }
            
             if (direcTagExecutable != null) {
                direcTagExecutableTitle = direcTagExecutable.getName();
                direcTagFolder = direcTagExecutable.getParentFile();
            } else if (new File(getJarFilePath() + "/resources/DirecTag").exists()) {

                // use the default DirecTag folder if not set by user
                pepNovoFolder = new File(getJarFilePath() + "/resources/DirecTag");

                // OS check
                String osName = System.getProperty("os.name").toLowerCase();

//                if (osName.contains("mac os")) {
//                    executableTitle = "PepNovo_Mac"; // @TODO: is Mac supported via the linux option?
                if (osName.contains("windows")) {
                    direcTagExecutableTitle = "DirecTag_Windows.exe";
                } else if (osName.indexOf("nix") != -1 || osName.indexOf("nux") != -1) {
                    direcTagExecutableTitle = "DirecTag_Linux";
                } else {
                    // unsupported OS version
                }
            }

            // check if the PepNovo folder is set
            if (pepNovoFolder == null && runPepNovo) {
                waitingHandlerCLIImpl.appendReport("\nPepNovo+ location not set! Sequencing canceled.", false, true);
                System.exit(0);
            }

            // check of the PepNovo executable is set
            if (pepNovoExecutableTitle == null && runPepNovo) {
                waitingHandlerCLIImpl.appendReport("\nPepNovo+ executable not set! Sequencing canceled.", false, true);
                System.exit(0);
            }
            
            // check if the DirecTag folder is set
            if (direcTagFolder == null && runDirecTag) {
                waitingHandlerCLIImpl.appendReport("\nDirecTag location not set! Sequencing canceled.", false, true);
                System.exit(0);
            }

            // check of the DirecTag executable is set
            if (direcTagExecutableTitle == null && runDirecTag) {
                waitingHandlerCLIImpl.appendReport("\nDirecTag executable not set! Sequencing canceled.", false, true);
                System.exit(0);
            }
            
            if (!runPepNovo && !runDirecTag) {
                waitingHandlerCLIImpl.appendReport("\nNeither PepNovo+ or DirecTag is selected! Sequencing canceled.", false, true);
                System.exit(0);
            }
            
//            if (runPepNovo && runDirecTag) {
//                waitingHandlerCLIImpl.appendReport("\nPepNovo+ and DirecTag cannot be selected at the same time! Sequencing canceled.", false, true);
//                System.exit(0);
//            }

            // check precursor tolerance, max is 5, but default for search params is 10...
            if (deNovoCLIInputBean.getSearchParameters().getPrecursorAccuracyDalton() > 5) {
                waitingHandlerCLIImpl.appendReport("\nPrecursor tolerance has to be between 0 and 5.0!", false, true);
                System.exit(0);
            }

            // starting the DeNovoCLI
            waitingHandlerCLIImpl.appendReportEndLine();
            waitingHandlerCLIImpl.appendReport("Starting DeNovoCLI.", true, true);
            waitingHandlerCLIImpl.appendReportEndLine();

            // Load the spectra into the factory
            SpectrumFactory spectrumFactory = SpectrumFactory.getInstance();
            waitingHandlerCLIImpl.appendReport("Loading the spectra.", true, true);
            for (File spectrumFile : deNovoCLIInputBean.getSpectrumFiles()) {
                spectrumFactory.addSpectra(spectrumFile);
            }
            waitingHandlerCLIImpl.appendReport("Done loading the spectra.", true, true);

            // start the sequencing
            DeNovoSequencingHandler searchHandler = new DeNovoSequencingHandler(pepNovoFolder, direcTagFolder);
            searchHandler.setNThreads(deNovoCLIInputBean.getNThreads());
            searchHandler.startSequencing(deNovoCLIInputBean.getSpectrumFiles(),
                    deNovoCLIInputBean.getSearchParameters(),
                    deNovoCLIInputBean.getOutputFile(), pepNovoExecutableTitle, direcTagExecutableTitle, runPepNovo, runDirecTag, waitingHandlerCLIImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sets the path configuration.
     */
    private void setPathConfiguration() throws IOException {
        File pathConfigurationFile = new File(getJarFilePath(), DenovoguiPathPreferences.configurationFileName);
        if (pathConfigurationFile.exists()) {
            DenovoguiPathPreferences.loadPathPreferencesFromFile(pathConfigurationFile);
        }
    }

    /**
     * DeNovoCLI header message when printing the usage.
     */
    private static String getHeader() {
        return System.getProperty("line.separator")
                + "DeNovoCLI performs de novo sequencing using the PepNovo+ algoritm." + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Spectra must be provided in the Mascot Generic File (mgf) format." + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "The identification parameters can be provided as a file as saved from the GUI or generated using the IdentificationParametersCLI." + System.getProperty("line.separator")
                + "See http://code.google.com/p/compomics-utilities/wiki/IdentificationParametersCLI for more details." + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "For further help see http://denovogui.googlecode.com and http://code.google.com/p/denovogui/wiki/DeNovoCLI." + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Or contact the developers at http://groups.google.com/group/denovogui." + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "----------------------"
                + System.getProperty("line.separator")
                + "OPTIONS"
                + System.getProperty("line.separator")
                + "----------------------" + System.getProperty("line.separator")
                + "\n";
    }

    /**
     * Starts the launcher by calling the launch method. Use this as the main
     * class in the jar file.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new DeNovoCLI(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the path to the jar file.
     *
     * @return the path to the jar file
     */
    public String getJarFilePath() {
        return CompomicsWrapper.getJarFilePath(this.getClass().getResource("DeNovoCLI.class").getPath(), "DeNovoGUI");
    }
}
