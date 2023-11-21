import axios from "axios";
import React, { useEffect, useState } from "react";
import Slider from "@mui/material/Slider";
import DBHHistogram from "./chart/DBHHistogram";

interface QueryConstructorProps {
  onCreateQuery: (newQuery: { title: string; content: string }) => void;
}

const QueryConstructor: React.FC<QueryConstructorProps> = ({
  onCreateQuery,
}) => {
  // State variables for selected criteria and input values
  const [selectedSpeciesGroup, setSelectedSpeciesGroup] = useState<string>("");
  const [selectedTreeStatus, setSelectedTreeStatus] = useState<string>("");
  const [selectedDiameterHeight, setSelectedDiameterHeight] =
    useState<string>("");
  const [title, setTitle] = useState("");
  const [treeTypes, setTreeTypes] = useState<any[]>([]);
  const [treeStatus, setTreeStatus] = useState<any[]>([]);
  const [heightRange, setHeightRange] = useState<number[]>([0, 350]);
  const [treeDiameter, setTreeDiameter] = useState<any[]>([]);
  const [selectedYear, setSelectedYear] = useState<number[]>([2000, 2023]);
  const [dataQueryResponse, setDataQueryResponse] = useState<any[]>([]);

  // Handle change for the year slider
  const handleYearChange = (event: Event, newValue: number | number[]) => {
    setSelectedYear(newValue as number[]);
  };

  // State variable for result limit with default value
  const [resultLimit, setResultLimit] = useState<number>(10);

  // Build the query based on selected criteria
  const handleBuildQuery = () => {
    const yearFilter =
      selectedYear.length === 2
        ? `AND tree_inventory_year BETWEEN ${selectedYear[0]} AND ${selectedYear[1]}`
        : selectedYear.length === 1
        ? `AND tree_inventory_year = ${selectedYear[0]}`
        : "";

    const limit = 40;

    const query = `SELECT DISTINCT(tree_record_number), species_common_name, species_group_code_name, tree_status_code_name, diameter_height_code_name, actual_height FROM \`bigquery-public-data.usfs_fia.tree\` WHERE 1=1 ${yearFilter} ${
      selectedSpeciesGroup
        ? `AND species_group_code_name='${selectedSpeciesGroup}'`
        : ""
    } ${
      selectedTreeStatus
        ? `AND tree_status_code_name='${selectedTreeStatus}'`
        : ""
    } ${
      selectedDiameterHeight
        ? `AND diameter_height_code_name='${selectedDiameterHeight}'`
        : ""
    } LIMIT ${limit}`;

    return query;
  };

  // Fetch data for dropdowns when the component mounts
  useEffect(() => {
    const fetchData = async () => {
      try {
        const query =
          "SELECT DISTINCT(species_group_code_name) FROM `bigquery-public-data.usfs_fia.tree`";
        const response = await axios.get(
          `http://localhost:8080/api/queries/execute?queryString=${encodeURIComponent(
            query
          )}`
        );
        setTreeTypes(response.data);

        const queryStatus =
          "SELECT DISTINCT(tree_status_code_name) FROM `bigquery-public-data.usfs_fia.tree`";
        const responseStatus = await axios.get(
          `http://localhost:8080/api/queries/execute?queryString=${encodeURIComponent(
            queryStatus
          )}`
        );
        setTreeStatus(responseStatus.data);

        const queryDiameter =
          "SELECT DISTINCT(diameter_height_code_name) FROM `bigquery-public-data.usfs_fia.tree`";
        const responDiameter = await axios.get(
          `http://localhost:8080/api/queries/execute?queryString=${encodeURIComponent(
            queryDiameter
          )}`
        );
        setTreeDiameter(responDiameter.data);
      } catch (error) {
        console.error("Error fetching data from the API", error);
      }
    };

    fetchData();
  }, []);

  // Execute data query when button is clicked
  const handleExecuteData = async () => {
    const query = handleBuildQuery();
    console.log(query);

    const respon = await axios.get(
      `http://localhost:8080/api/queries/execute?queryString=${encodeURIComponent(
        query
      )}`
    );
    console.log(respon.data);

    setDataQueryResponse(respon.data);
  };

  // Handle change for the height slider
  const handleHeightChange = (event: Event, newValue: number | number[]) => {
    setHeightRange(newValue as number[]);
  };

  // Handle form submission to create a new query
  const handleCreateQuery = (e: React.FormEvent) => {
    const content = handleBuildQuery();
    onCreateQuery({ title, content });
  };

  return (
    <>
      <form onSubmit={handleCreateQuery}>
        <div>
          <h3>Query Constructor</h3>
          {/* Input for the query title */}
          <label>
            Title:
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </label>
          {/* Slider for the year range */}
          <div>
            <label>Year Range:</label>
            <Slider
              value={selectedYear}
              onChange={handleYearChange}
              valueLabelDisplay="auto"
              valueLabelFormat={(value) => `${value}`}
              min={2000}
              max={2023}
            />
          </div>
          {/* Dropdown for tree types */}
          <div>
            <label>Tree Type:</label>
            <select
              value={selectedSpeciesGroup}
              onChange={(e) => setSelectedSpeciesGroup(e.target.value)}
            >
              <option value="">All</option>
              {treeTypes.map((treeType) => (
                <option
                  key={treeType.species_group_code_name}
                  value={treeType.species_group_code_name}
                >
                  {treeType.species_group_code_name}
                </option>
              ))}
            </select>
          </div>
          {/* Dropdown for tree status */}
          <div>
            <label>Tree Status:</label>
            <select
              value={selectedTreeStatus}
              onChange={(e) => setSelectedTreeStatus(e.target.value)}
            >
              <option value="">All</option>
              {treeStatus.map((treeState) => (
                <option
                  key={treeState.tree_status_code_name}
                  value={treeState.tree_status_code_name}
                >
                  {treeState.tree_status_code_name}
                </option>
              ))}
            </select>
          </div>
          {/* Dropdown for tree diameter */}
          <div>
            <label>Tree Diameter:</label>
            <select
              value={selectedDiameterHeight}
              onChange={(e) => setSelectedDiameterHeight(e.target.value)}
            >
              <option value="">All</option>
              {treeDiameter.map((treeDiameter) => (
                <option
                  key={treeDiameter.diameter_height_code_name}
                  value={treeDiameter.diameter_height_code_name}
                >
                  {treeDiameter.diameter_height_code_name}
                </option>
              ))}
            </select>
          </div>
          {/* Slider for tree height range */}
          <div>
            <label>Tree Height (Range):</label>
            <Slider
              value={heightRange}
              onChange={handleHeightChange}
              valueLabelDisplay="auto"
              valueLabelFormat={(value) => `${value} feet`}
              min={0}
              max={350}
            />
          </div>
          {/* Slider for result limit */}

          {/* Button to submit the form and create a new query */}
          <div>
            <input type="submit" value="Create Query" />
          </div>
        </div>
      </form>
      <br />
      <br />
      <br />
      {/* Button to execute the data query */}
      <div>
        <button onClick={handleExecuteData}>Execute Query</button>
      </div>
      {/* Render charts if data is available */}
      {dataQueryResponse.length > 0 ? (
        <>
          <DBHHistogram data={dataQueryResponse} />
        </>
      ) : (
        <></>
      )}
    </>
  );
};

export default QueryConstructor;
