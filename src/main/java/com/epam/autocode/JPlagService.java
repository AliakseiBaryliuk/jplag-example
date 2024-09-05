package com.epam.autocode;

import de.jplag.JPlag;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;
import de.jplag.java.Language;
import de.jplag.options.JPlagOptions;
import de.jplag.reporting.reportobject.ReportObjectFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JPlagService {

    public JPlagResult run() {
        Set<File> submissionDirectories;
        JPlagResult result = null;

        var language = new Language();
        var rootPath = Paths.get("C:\\EPAM_MY\\jplag\\jplag-example\\work");
        var sub = rootPath.resolve("submissions");

        try (Stream<Path> stream = Files.list(sub)) {
            submissionDirectories = stream.map(Path::toFile).collect(Collectors.toSet());
            var baseCode = Paths.get("C:\\EPAM_MY\\jplag\\jplag-example\\src\\main\\java\\com\\epam\\autocode\\base");
            var options = new JPlagOptions(language, submissionDirectories, Set.of()).withBaseCodeSubmissionDirectory(baseCode.toFile()).withMinimumTokenMatch(5);
            JPlag jPlag = new JPlag(options);

            result = jPlag.run();

            var output = Paths.get("output");
            var reportObjectFactory = new ReportObjectFactory();
            reportObjectFactory.createAndSaveReport(result, output.toString());
        } catch (IOException | ExitException e) {
            throw new RuntimeException("Error while reading submission directories", e);
        }
        return result;
    }
}
