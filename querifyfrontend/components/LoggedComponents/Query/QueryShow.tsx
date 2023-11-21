import React from "react";

// Define the interface for the props that the QueryShow component receives
interface QueryShowProps {
  query: string;  // The query string to be displayed
}

// Define the QueryShow component using the provided props
const QueryShow: React.FC<QueryShowProps> = ({ query }) => {
  // If the query is not available, display a message
  if (!query) {
    return <div>Consulta no disponible</div>;
  }

  // Regular expression to extract the range of years from the query
  const regex = /AND tree_inventory_year BETWEEN (\d+) AND (\d+)/;
  const yearMatch = query.match(regex);

  // Generate a summary for the year range based on the matched values
  const yearSummary =
    yearMatch && yearMatch.length === 3
      ? `Rango de Años: [${yearMatch[1]}, ${yearMatch[2]}]`
      : "Años: Todos";

  // Generate summaries based on the presence of specific query parameters
  const speciesGroupSummary = query.includes("species_group_code_name=")
    ? `Tipo de Árbol: Específico`
    : "Tipos de Árbol: Todos";

  const treeStatusSummary = query.includes("tree_status_code_name=")
    ? `Estado del Árbol: Específico`
    : "Estados del Árbol: Todos";

  const diameterHeightSummary = query.includes("diameter_height_code_name=")
    ? `Tamaño del Diámetro: Específico`
    : "Tamaños del Diámetro: Todos";

  // Render the QueryShow component with the generated summaries
  return (
    <div>
      {/* Display the summaries */}
      <div>{yearSummary}</div>
      <div>{speciesGroupSummary}</div>
      <div>{treeStatusSummary}</div>
      <div>{diameterHeightSummary}</div>
    </div>
  );
};

export default QueryShow;
