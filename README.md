# Nearest Neighbours Mean Shift LSH

This algorithm was created during an internship at Laboratoire d'Informatique de Paris Nord with Lebbah Mustapha, Duong Tarn, Azzag Hanene and Beck Gaël.
It's purpose is clustering of multivariate multidimensional dataset, especialy image.
Mean Shift strenght is automatic detection of cluster number and contrary to K-means it can detect non eliptical clusters.

## Recomandations

It is recommand to normalize data unless your data has already features with the same order of magnitude due to distance computation.

### Paramaters

* **k** is the number of neighbours to look at in order to compute centoid.
* **nbseg** number of segment on which we project vectors during LSH, this parameter should be big enough, under 20 results could be wrong depending on your data.
* **nbblocs** parameter is the bottleneck of this algorithm, the bigger it is the faster it compute but you risk to loose quality.
* **cmin** is the threshold under which we fusion little cluster with the nearest cluster.
* **normalisation** define if you want normalize your data following this formula (X-Xmin)/(Xmax-Xmin).
* **w** is a uniformisation constant for LSH.
* **npPart** is the default parallelism outside the gradient ascent.
* **iterForYstar** is the number of iteration in gradient ascent
* **threshold_clust** is the threshold under which we give the same label to two points

### Image analysis
In order to do image analysis it is recommand to convert data from RGB to Luv space and adding space index.

## Usage
This algorithm is build to work with indexed dataset. Usage is preety simple. Prepare your parsed dataset giving him index and rest of data. Use other function to save result or make prediction for new data.

```scala

  val sc = new SparkContext(conf)
  val defp = sc.defaultParallelism
  val meanShift = msLsh.MsLsh
  val data = sc.textFile("myIndexedData.csv",defp)
  val parsedData = data.map(x => x.split(',')).map(y => (y(0),Vectors.dense(y.tail.map(_.toDouble)))).cache
  
  val model = meanShift.train(  sc,
                          parsedData,
                          k=60,
                          threshold_clust=0.05,
                          iterForYstar=10,
                          cmin=0,
                          normalisation=true,
                          w=1,
                          nbseg=100,
                          nbblocs=50,
                          nbPart=defp)  
                          
  // Save result for an image as (ID, Vector, ClusterNumber)
  meanShift.saveImageAnalysis(sc, model, "MyImageResultDirectory",1)

  // Save result as (ID, ClusterNumber)
  meanShift.savelabeling(model, "MyResultDirectory", 1)

  // Save centroids result as (NumCluster, cardinality, CentroidVector)
  meanShift.saveCentroid(sc, model, "centroidDirectory")

```

## Image segmentation example

The picture on top left corner is the #117 from Berkeley Segmentation Dataset and Benchmark repository. Others are obtained with :
* **nbblocs** of 200,500,1000
* **k** : 50
* **iterForYstar** : 10
* **w** : 1
* **cmin** : 200
![alt text][logo]

[logo]: http://img11.hostingpics.net/pics/393309flower.png
