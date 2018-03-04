/*
 * HelloUniverse.java
 * Copyright (c) 2006 Sun Microsystems, Inc.

 * Simple Java 3D example program to display a spinning cube.
*/


import java.awt.*;
import javax.swing.*;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.*;
import javax.vecmath.*;


public class HelloUniverse extends JFrame
{
  private JPanel drawingPanel;

  private SimpleUniverse univ = null;
  private BranchGroup scene = null;


  public HelloUniverse()
  {
    initComponents();

    // Create Canvas3D and SimpleUniverse; add canvas to drawing panel
    Canvas3D c = createUniverse();

    drawingPanel.add(c, BorderLayout.CENTER);

    // Create the content branch and add it to the universe
    scene = createSceneGraph();
    univ.addBranchGraph(scene);
  }  // end of HelloUniverse()

    
  private void initComponents()
  {
    drawingPanel = new JPanel();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("HelloUniverse");
    drawingPanel.setLayout(new java.awt.BorderLayout());

    drawingPanel.setPreferredSize(new Dimension(250, 250));
    getContentPane().add(drawingPanel, BorderLayout.CENTER);

    pack();
  }  // end of initComponents()


  private Canvas3D createUniverse()
  {
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D c = new Canvas3D(config);

    univ = new SimpleUniverse(c);

    // This will move the ViewPlatform back a bit so the
    // objects in the scene can be viewed.
    univ.getViewingPlatform().setNominalViewingTransform();

    // Ensure at least 5 msec per frame (i.e., < 200Hz)
    univ.getViewer().getView().setMinimumFrameCycleTime(5);

    return c;
  } // end of createUniverse()



  public BranchGroup createSceneGraph()
  {
    // Create the root of the branch graph
    BranchGroup objRoot = new BranchGroup();

    // Create the TransformGroup node and initialize it to the
    // identity. Enable the TRANSFORM_WRITE capability so that
    // our behavior code can modify it at run time. Add it to
    // the root of the subgraph.
    TransformGroup objTrans = new TransformGroup();

    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objRoot.addChild(objTrans);

    // Create a simple Shape3D node; add it to the scene graph.
    objTrans.addChild(new ColorCube(0.4));

    // Create a new Behavior object that will perform the
    // desired operation on the specified transform and add
    // it into the scene graph.
    Transform3D yAxis = new Transform3D();
    Alpha rotationAlpha = new Alpha(-1, 4000);

    RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
        objTrans, yAxis, 0.0f, (float) Math.PI * 2.0f);
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

    rotator.setSchedulingBounds(bounds);
    objRoot.addChild(rotator);

    // Have Java 3D perform optimizations on this scene graph.
    objRoot.compile();

    return objRoot;
  } // end of createSceneGraph()


// -------------------------------------------------------

  public static void main(String args[])
  {
    EventQueue.invokeLater(new Runnable()
    { public void run()
      {  new HelloUniverse().setVisible(true);  }
    });
  } // end of main()
    
} // end of HelloUniverse class
