package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.SpecializationsList;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Main7 {
    public static void main(String[] args) {
        int year = 2025;
        String deadlines = specializationDeadlines(year);
        System.out.println(deadlines);
    }

    public static String specializationDeadlines(int year) {
        String json = Api.getSpecializations(year);
        SpecializationsList specializations = new Gson().fromJson(json, SpecializationsList.class);



        return specializations.items.stream()
                .filter(spec -> spec.eprDeadlinePrihlaska != null && spec.eprDeadlinePrihlaska.value != null)
                .map(spec -> spec.eprDeadlinePrihlaska.value)
                .distinct()
                .sorted(Comparator.comparing((String date) -> {
                    String[] parts = date.split("\\.");
                    return Integer.parseInt(parts[2]) * 10000 + Integer.parseInt(parts[1]) * 100
                            + Integer.parseInt(parts[0]);
                }))
                .collect(Collectors.joining(","));
    }
}
