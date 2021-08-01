--Productos

SELECT 

a.id as productoid, 
SUM(ast.stock) as stock, 
CONCAT('prod',a.id) as producto, 
fam.familiaid,
ad.fecha,
ad.preciominoristamoneda as vprecio, 
a.descripcionext,
costo.costofinal as costo 

FROM articulo a

JOIN articulostock ast ON ast.idarticulo = a.id
JOIN (

	SELECT ac.idarticulo as idarticulo, 
	cs.idcategoria as familiaid 
	FROM articulo_categoriasubvalor ac
	LEFT JOIN categoriasubvalor csv 
		ON csv.id = ac.idcategoriasubvalor
	LEFT JOIN categoriasub cs 
		ON cs.id = csv.idcategoriasub
	GROUP BY ac.idarticulo, cs.idcategoria

) fam ON fam.idarticulo = a.id

LEFT JOIN (
	SELECT a.idarticulo, 
	MAX(a.fecha) as fecha, 
	MAX(a.preciominoristamoneda) as preciominoristamONeda 
	FROM articulopreciodeposito a
	GROUP BY a.idarticulo

	) ad ON ad.idarticulo = a.id

JOIN (
	SELECT 
	DISTINCT ON (idarticulo) idarticulo, 
	fechaINicio, 
	(costofinalmoneda * cambiomoneda) as costofinal  
	FROM articuloultimocosto 
	GROUP BY idarticulo, 
		costofinalmoneda, fechainicio, cambiomoneda
	ORDER BY idarticulo, fechaINicio desc) 
		costo ON costo.idarticulo = a.id 
WHERE ast.iddeposito NOT IN (7,4,3,5) 
GROUP BY a.id, fam.familiaid, ad.fecha,
    ad.preciominoristamoneda, costo.costofinal
ORDER BY a.id;	