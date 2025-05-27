package org.angelesyvalientes.api.dto;

import java.util.Map;

public record MesProgramaDTO(String mes, Map<String, Integer> programas) {
}
