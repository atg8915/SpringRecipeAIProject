package com.sist.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import com.sist.web.vo.*;
@Mapper
@RequestMapping
public interface PostgresMapper {
	//<select id="findSimilarRecipes" resultType="hashmap">
	public List<Map<String, Object>> findSimilarRecipes( 
			@Param("embedding") String embedding, 
			@Param("limit") int limit );
}
