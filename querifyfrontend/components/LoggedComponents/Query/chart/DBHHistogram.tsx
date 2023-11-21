import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

// Define the data item structure
interface DataItem {
  tree_record_number: string;
  actual_height: string;
}

// Define the props for the Histogram component
interface HistogramProps {
  data: DataItem[];
}

// Histogram component
const Histogram: React.FC<HistogramProps> = ({ data }) => {
  // Create a reference for the SVG element
  const svgRef = useRef<SVGSVGElement>(null);

  // useEffect hook to handle histogram creation and updates
  useEffect(() => {
    // Select the SVG element using d3
    const svg = d3.select(svgRef.current);
    svg.selectAll("*").remove(); // Clear the SVG

    // Define margins and dimensions
    const margin = { top: 20, right: 20, bottom: 30, left: 40 };
    const width = 600 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    // Filter data to remove entries without necessary fields
    const filteredData = data.filter(
      (d) => d.tree_record_number && d.actual_height
    );

    // Convert heights to numbers and filter out incorrect data
    const heights = filteredData.map((d) => Number(d.actual_height) || 0);

    // Create x scale
    const xScale = d3
      .scaleLinear()
      .domain([d3.min(heights) || 0, d3.max(heights) || 0])
      .range([0, width]);

    // Create histogram function
    const histogram = d3
      .histogram<number, number>()
      .value((d: any) => d)
      .domain(xScale.domain() as [number, number])
      .thresholds(xScale.ticks(10));

    // Generate bins using the histogram function
    const bins = histogram(heights);

    // Create y scale
    const yScale = d3
      .scaleLinear()
      .domain([0, d3.max(bins, (d: any) => d.length) || 0])
      .range([height, 0]);

    // Create a new group element within the SVG
    const g = svg
      .append("g")
      .attr("transform", `translate(${margin.left},${margin.top})`);

    // Create x-axis
    g.append("g")
      .attr("transform", `translate(0,${height})`)
      .call(d3.axisBottom(xScale));

    // Create y-axis
    g.append("g").call(d3.axisLeft(yScale));

    // Create bars
    g.selectAll(".bar")
      .data(bins)
      .enter()
      .append("rect")
      .attr("class", "bar")
      .attr("x", (d: any) => xScale(d.x0))
      .attr("y", (d: any) => yScale(d.length))
      .attr("width", (d: any) => xScale(d.x1) - xScale(d.x0) - 1)
      .attr("height", (d: any) => height - yScale(d.length));
  }, [data]);

  // Return the JSX for rendering
  return (
    <div>
      <h3>Histogram - Tree Height Distribution</h3>
      <svg ref={svgRef} width={600} height={400}></svg>
    </div>
  );
};

export default Histogram;
