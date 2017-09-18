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

package tum.cms.sim.momentum.model.operational.walking.socialForceModel.sharedSpaces_Zeng2014;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import tum.cms.sim.momentum.utility.geometry.Ellipse2D;
import tum.cms.sim.momentum.utility.geometry.GeometryFactory;
import tum.cms.sim.momentum.utility.geometry.Rectangle2D;
import tum.cms.sim.momentum.utility.geometry.Vector2D;

public class SharedSpaceUnitTest {
	
	private static double PRECISION = 1E-15;

	@Test
	public void testGetRelativeAngle() {
		Vector2D mePos, meDirection, youPos;
		
		mePos = GeometryFactory.createVector(0, 0);
		meDirection = GeometryFactory.createVector(0, 0);
		youPos = GeometryFactory.createVector(0, 0);
        Assert.assertEquals(0.0, SharedSpacesComputations.getRelativeAngle(mePos, meDirection, youPos), PRECISION);
        
        mePos = GeometryFactory.createVector(0, 1);
        meDirection = GeometryFactory.createVector(-1, 0);
        youPos = GeometryFactory.createVector(1, 2);
        Assert.assertEquals(135.0, SharedSpacesComputations.getRelativeAngle(mePos, meDirection, youPos), PRECISION);
        
        mePos = GeometryFactory.createVector(0, 1);
        meDirection = GeometryFactory.createVector(0, 1);
        youPos = GeometryFactory.createVector(1, 2);
        Assert.assertEquals(45.0, SharedSpacesComputations.getRelativeAngle(mePos, meDirection, youPos), PRECISION);
        
        mePos = GeometryFactory.createVector(2, 1);
        meDirection = GeometryFactory.createVector(0, 1);
        youPos = GeometryFactory.createVector(1, 2);
        Assert.assertEquals(-45.0, SharedSpacesComputations.getRelativeAngle(mePos, meDirection, youPos), PRECISION);
        
        mePos = GeometryFactory.createVector(2, 1);
        meDirection = GeometryFactory.createVector(0, 1);
        youPos = GeometryFactory.createVector(2, 0);
        Assert.assertEquals(180.0, SharedSpacesComputations.getRelativeAngle(mePos, meDirection, youPos), PRECISION);
        
        mePos = GeometryFactory.createVector(-1, -1);
        meDirection = GeometryFactory.createVector(-1, -1);
        youPos = GeometryFactory.createVector(-1, 0);
        Assert.assertEquals(135.0, SharedSpacesComputations.getRelativeAngle(mePos, meDirection, youPos), PRECISION);
	}
	
	@Test
	public void testGetTimeToConflictPoint() {
		Vector2D mePosition, meVelocity, youPosition, youVelocity;
		double modelComputationalPrecision = 1E-9;
		
		// vertical velocity test
		mePosition = GeometryFactory.createVector(0, 0);
		meVelocity = GeometryFactory.createVector(0, 1);
		youPosition = GeometryFactory.createVector(2, 2);
		youVelocity = GeometryFactory.createVector(-1, 0);
		Assert.assertEquals(0.0, SharedSpacesComputations.getTimeToConflictPoint(mePosition, meVelocity,
				youPosition, youVelocity, modelComputationalPrecision), PRECISION);
		
		mePosition = GeometryFactory.createVector(0, 0);
		meVelocity = GeometryFactory.createVector(1, 1);
		youPosition = GeometryFactory.createVector(2, 0);
		youVelocity = GeometryFactory.createVector(-1, 1);
		Assert.assertEquals(0.0, SharedSpacesComputations.getTimeToConflictPoint(mePosition, meVelocity,
				youPosition, youVelocity, modelComputationalPrecision), PRECISION);
		
		mePosition = GeometryFactory.createVector(0, 0);
		meVelocity = GeometryFactory.createVector(0, 1);
		youPosition = GeometryFactory.createVector(1, 0);
		youVelocity = GeometryFactory.createVector(-1, -1);
		Assert.assertEquals(Double.POSITIVE_INFINITY, SharedSpacesComputations.getTimeToConflictPoint(mePosition, meVelocity,
				youPosition, youVelocity, modelComputationalPrecision), PRECISION);
		
		// collision point, which you reach after 1 and i reach after 11
		mePosition = GeometryFactory.createVector(0, 0);
		meVelocity = GeometryFactory.createVector(1, 0);
		youPosition = GeometryFactory.createVector(11, 1);
		youVelocity = GeometryFactory.createVector(0, -1);
		Assert.assertEquals(10.0, SharedSpacesComputations.getTimeToConflictPoint(mePosition, meVelocity,
				youPosition, youVelocity, modelComputationalPrecision), PRECISION);
		
		mePosition = GeometryFactory.createVector(0, 0);
		meVelocity = GeometryFactory.createVector(0, 1);
		youPosition = GeometryFactory.createVector(0, 10);
		youVelocity = GeometryFactory.createVector(0, -1);
		Assert.assertEquals(0.0, SharedSpacesComputations.getTimeToConflictPoint(mePosition, meVelocity,
				youPosition, youVelocity, modelComputationalPrecision), PRECISION);
	}

}