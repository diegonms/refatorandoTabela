package br.pucpr.planet;

import br.pucpr.table.TableData;
import java.util.List;
import java.util.Objects;

/** Adapta uma lista de {@link Planet} para a interface {@link TableData}. */
public class PlanetsTableData implements TableData {
  private static final String[] HEADERS = {
    "%-20s".formatted("Nome"),
    "%-10s".formatted("Diâmetro"),
    "%-15s".formatted("Dist. sol (km)"),
    "%-15s".formatted("Dist. sol (ua)"),
    "%-10s".formatted("Tipo")
  };

  private final List<Planet> planets;

  public PlanetsTableData(List<Planet> planets) {
    this.planets =
        planets == null ? List.of() : planets.stream().filter(Objects::nonNull).toList();
  }

  @Override
  public int getColumnCount() {
    return HEADERS.length;
  }

  @Override
  public String getHeader(int column) {
    return HEADERS[column];
  }

  @Override
  public int getRowCount() {
    return planets.size();
  }

  @Override
  public String getValue(int row, int column) {
    final var planet = planets.get(row);
    return switch (column) {
      case 0 -> formatName(planet.name());
      case 1 -> "%,.1f".formatted(planet.diameterKm());
      case 2 -> "%,d".formatted(planet.sunDistanceKm());
      case 3 -> "%.02f".formatted(Planet.kmToAu(planet.sunDistanceKm()));
      case 4 -> formatType(planet.type());
      default -> throw new IndexOutOfBoundsException(column);
    };
  }

  @Override
  public boolean isRightAligned(int column) {
    return column >= 1 && column <= 3;
  }

  private static String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return "NÃO INFORMADO";
    }
    if (name.length() > 20) {
      return name.substring(0, 17) + "...";
    }
    return name;
  }

  private static String formatType(PlanetType type) {
    return switch (type) {
      case ROCK -> "Rochoso";
      case GAS -> "Gasoso";
      case ICE -> "Gelado";
      case DWARF -> "Anão";
    };
  }
}
