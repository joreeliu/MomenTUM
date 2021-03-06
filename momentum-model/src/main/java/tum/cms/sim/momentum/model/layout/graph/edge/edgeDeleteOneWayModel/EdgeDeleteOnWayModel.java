/*******************************************************************************
 * Welcome to the pedestrian simulation framework MomenTUM. 
 * This file belongs to the MomenTUM version 2.0.2.
 * 
 * This software was developed under the lead of Dr. Peter M. Kielar at the
 * Chair of Computational Modeling and Simulation at the Technical University Munich.
 * 
 * All rights reserved. Copyright (C) 2017.
 * 
 * Contact: peter.kielar@tum.de, https://www.cms.bgu.tum.de/en/
 * 
 * Permission is hereby granted, free of charge, to use and/or copy this software
 * for non-commercial research and education purposes if the authors of this
 * software and their research papers are properly cited.
 * For citation information visit:
 * https://www.cms.bgu.tum.de/en/31-forschung/projekte/456-momentum
 * 
 * However, further rights are not granted.
 * If you need another license or specific rights, contact us!
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package tum.cms.sim.momentum.model.layout.graph.edge.edgeDeleteOneWayModel;

import java.util.ArrayList;
import java.util.List;

import tum.cms.sim.momentum.data.layout.obstacle.OneWayWallObstacle;
import tum.cms.sim.momentum.infrastructure.execute.SimulationState;
import tum.cms.sim.momentum.model.layout.graph.GraphOperation;
import tum.cms.sim.momentum.utility.geometry.GeometryFactory;
import tum.cms.sim.momentum.utility.geometry.Segment2D;
import tum.cms.sim.momentum.utility.geometry.operation.GeometryAdditionals;
import tum.cms.sim.momentum.utility.graph.Edge;
import tum.cms.sim.momentum.utility.graph.Graph;

public class EdgeDeleteOnWayModel extends GraphOperation {

	@Override
	public void callPreProcessing(SimulationState simulationState) {
		
		List<OneWayWallObstacle> oneWayWalls = this.scenarioManager.getOneWayWalls();
		Graph graph = this.scenarioManager.getGraph();
		
		ArrayList<Edge> removeEdges = new ArrayList<>();
			
		oneWayWalls.forEach(oneWayWall -> 
			graph.getAllEdges().forEach(edge -> {
				
				Segment2D edgeSegment = null;
				
				edgeSegment = GeometryFactory.createSegment(
					edge.getStart().getGeometry().getCenter(),
					edge.getEnd().getGeometry().getCenter());

				Segment2D element = ((Segment2D)oneWayWall.getGeometry());
				
				if(edgeSegment.getIntersection(element).size() > 0) {
					
					if(GeometryAdditionals.isLeftOf(element.getFirstPoint(), element.getLastPoint(), edge.getEnd().getGeometry().getCenter()) !=
							GeometryAdditionals.isLeftOf(element.getFirstPoint(), element.getLastPoint(), oneWayWall.getDirection())) {
						
						removeEdges.add(edge);
					}
				}
			})
		);
		
		removeEdges.forEach(remove -> graph.disconnectDirectedVertices(remove.getStart(), remove.getEnd()));
	}

	@Override
	public void callPostProcessing(SimulationState simulationState) {
		// nothing to do
	}

}
