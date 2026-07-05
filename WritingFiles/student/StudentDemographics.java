package WritingFiles.student;

/* 觀念：修改後的 Record 實作，負責將學生的基本統計資料格式化輸出 */

/* 觀念：修改後的 Record 實作，負責將學生的基本統計資料格式化輸出 */
public record StudentDemographics(String countryCode, int enrolledMonth,
        int enrolledYear, int ageAtEnrollment, String gender,
        boolean previousProgrammingExperience) {

    @Override
    /* 修改 toString 以便回傳以逗號分隔（comma delimited）的字串，方便導出 CSV */
    public String toString() {
        return "%s,%d,%d,%d,%s,%b".formatted(countryCode,
                enrolledMonth,enrolledYear, ageAtEnrollment,gender,
                previousProgrammingExperience);
            }
}