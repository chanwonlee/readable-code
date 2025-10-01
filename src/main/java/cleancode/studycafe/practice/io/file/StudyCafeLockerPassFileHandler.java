package cleancode.studycafe.practice.io.file;

import cleancode.studycafe.practice.exception.AppException;
import cleancode.studycafe.practice.model.StudyCafeLockerPass;
import cleancode.studycafe.practice.model.StudyCafePassType;
import cleancode.studycafe.practice.provider.StudyCafeLockerPassProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StudyCafeLockerPassFileHandler implements StudyCafeLockerPassProvider {

    public static final String STUDY_CAFE_LOCKER_DATA_PATH = "src/main/resources/cleancode/studycafe/locker.csv";

    @Override
    public List<StudyCafeLockerPass> getStudyCafeLockerPasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(STUDY_CAFE_LOCKER_DATA_PATH));

            return getStudyCafeLockerPasses(lines);
        } catch (IOException e) {
            throw new AppException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private static List<StudyCafeLockerPass> getStudyCafeLockerPasses(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(","))
                .map(values -> StudyCafeLockerPass.of(
                        StudyCafePassType.valueOf(values[0]),
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2])
                ))
                .toList();
    }
}
