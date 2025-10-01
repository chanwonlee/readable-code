package cleancode.studycafe.practice.io.file;

import cleancode.studycafe.practice.exception.AppException;
import cleancode.studycafe.practice.model.StudyCafePass;
import cleancode.studycafe.practice.model.StudyCafePassType;
import cleancode.studycafe.practice.provider.StudyCafePassProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StudyCafePassFileHandler implements StudyCafePassProvider {

    public static final String STUDY_CAFE_PASS_DATA_PATH = "src/main/resources/cleancode/studycafe/pass-list.csv";

    @Override
    public List<StudyCafePass> getStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(STUDY_CAFE_PASS_DATA_PATH));
            return parseStudyCafePassesFrom(lines);
        } catch (IOException e) {
            throw new AppException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private static List<StudyCafePass> parseStudyCafePassesFrom(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(","))
                .map(values -> StudyCafePass.of(
                        StudyCafePassType.valueOf(values[0]),
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]),
                        Double.parseDouble(values[3])
                ))
                .toList();
    }
}
