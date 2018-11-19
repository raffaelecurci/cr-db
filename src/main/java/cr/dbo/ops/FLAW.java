package cr.dbo.ops;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.SharedEntity;
import cr.shared.Flaw;

@Entity
@Table(name = "[FLAW]")
public class FLAW extends SharedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long analysis_size_bytes;
	private String rating;
	private String score;
	private Long id_commit;
	private Long loc;
	private Integer severity;
	private Integer categoryid;
	private String categoryname;
	@Column(columnDefinition = "varchar(MAX)")
	private String category_description;// VARCHAR(MAX)
	@Column(columnDefinition = "varchar(MAX)")
	private String recommendation_description;// VARCHAR(MAX)
	private Integer cweid;
	private String cwename;
	private Boolean cwepcirelated;
	@Column(columnDefinition = "varchar(MAX)")
	private String cwe_description;// VARCHAR(MAX)
	private String cia_impact;
	private Long count;
	@Column(columnDefinition = "varchar(MAX)")
	private String flaw_description;// VARCHAR(MAX)
	private Integer exploitLevel;
	private String functionprototype;
	private Integer functionrelativelocation;
	private Integer issueid;
	private Integer line;
	private Boolean pcirelated;
	private String scope;
	private String sourcefile;
	private String sourcefilepath;
	private String type;
	private Boolean affects_policy_compliance;
	private Long build_id;
	private Long app_id;

	
	public FLAW(Long id, Long analysis_size_bytes, String rating, String score, Long id_commit, Long loc,
			Integer severity, Integer categoryid, String categoryname, String category_description,
			String recommendation_description, Integer cweid, String cwename, Boolean cwepcirelated,
			String cwe_description, String cia_impact, Long count, String flaw_description, Integer exploitLevel,
			String functionprototype, Integer functionrelativelocation, Integer issueid, Integer line,
			Boolean pcirelated, String scope, String sourcefile, String sourcefilepath, String type,
			Boolean affects_policy_compliance,Long build_id,Long app_id) {
		super();
		this.id = id;
		this.analysis_size_bytes = analysis_size_bytes;
		this.rating = rating;
		this.score = score;
		this.id_commit = id_commit;
		this.loc = loc;
		this.severity = severity;
		this.categoryid = categoryid;
		this.categoryname = categoryname;
		this.category_description = category_description;// VARCHAR(MAX)
		this.recommendation_description = recommendation_description;// VARCHAR(MAX)
		this.cweid = cweid;
		this.cwename = cwename;
		this.cwepcirelated = cwepcirelated;
		this.cwe_description = cwe_description;// VARCHAR(MAX)
		this.cia_impact = cia_impact;
		this.count = count;
		this.flaw_description = flaw_description;// VARCHAR(MAX)
		this.exploitLevel = exploitLevel;
		this.functionprototype = functionprototype;
		this.functionrelativelocation = functionrelativelocation;
		this.issueid = issueid;
		this.line = line;
		this.pcirelated = pcirelated;
		this.scope = scope;
		this.sourcefile = sourcefile;
		this.sourcefilepath = sourcefilepath;
		this.type = type;
		this.affects_policy_compliance = affects_policy_compliance;
		this.app_id=app_id;
		this.build_id=build_id;
	}

	public FLAW(Long analysis_size_bytes, String rating, String score, Long id_commit, Long loc, Integer severity,
			Integer categoryid, String categoryname, String category_description, String recommendation_description,
			Integer cweid, String cwename, Boolean cwepcirelated, String cwe_description, String cia_impact, Long count,
			String flaw_description, Integer exploitLevel, String functionprototype, Integer functionrelativelocation,
			Integer issueid, Integer line, Boolean pcirelated, String scope, String sourcefile, String sourcefilepath,
			String type, Boolean affects_policy_compliance,Long build_id,Long app_id) {
		super();
		this.analysis_size_bytes = analysis_size_bytes;
		this.rating = rating;
		this.score = score;
		this.id_commit = id_commit;
		this.loc = loc;
		this.severity = severity;
		this.categoryid = categoryid;
		this.categoryname = categoryname;
		this.category_description = category_description;
		this.recommendation_description = recommendation_description;
		this.cweid = cweid;
		this.cwename = cwename;
		this.cwepcirelated = cwepcirelated;
		this.cwe_description = cwe_description;
		this.cia_impact = cia_impact;
		this.count = count;
		this.flaw_description = flaw_description;
		this.exploitLevel = exploitLevel;
		this.functionprototype = functionprototype;
		this.functionrelativelocation = functionrelativelocation;
		this.issueid = issueid;
		this.line = line;
		this.pcirelated = pcirelated;
		this.scope = scope;
		this.sourcefile = sourcefile;
		this.sourcefilepath = sourcefilepath;
		this.type = type;
		this.affects_policy_compliance = affects_policy_compliance;
		this.app_id=app_id;
		this.build_id=build_id;
	}

//	public FLAW() {
//		super();
//	}
	
	public FLAW(Flaw flaw) {
		List<Field> FLAWfields = Stream.of(this.getClass().getDeclaredFields())
		.filter(f -> (!f.getName().equals("id_commit") && !f.getName().equals("id"))
				&& !java.lang.reflect.Modifier.isStatic(f.getModifiers())).collect(Collectors.toList());
		for (int i = 0; i < FLAWfields.size(); i++) {
			Field f=FLAWfields.get(i);
//			System.out.println(f.getName());
			try {
//				System.out.println(Flaw.class.getDeclaredField(f.getName()));
				Field toCopy=Flaw.class.getDeclaredField(f.getName());
				boolean accessibility=toCopy.isAccessible();
				toCopy.setAccessible(true);
//				genericSetter(tmp, f, toCopy.get(flaw) );
				f.set(this, toCopy.get(flaw));
				toCopy.setAccessible(accessibility);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 			
		}
	}

	public Boolean getAffects_policy_compliance() {
		return affects_policy_compliance;
	}

	public void setAffects_policy_compliance(Boolean affects_policy_compliance) {
		this.affects_policy_compliance = affects_policy_compliance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAnalysis_size_bytes() {
		return analysis_size_bytes;
	}

	public void setAnalysis_size_bytes(Long analysis_size_bytes) {
		this.analysis_size_bytes = analysis_size_bytes;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Long getId_commit() {
		return id_commit;
	}

	public void setId_commit(Long id_commit) {
		this.id_commit = id_commit;
	}

	public Long getLoc() {
		return loc;
	}

	public void setLoc(Long loc) {
		this.loc = loc;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getCategory_description() {
		return category_description;
	}

	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}

	public String getRecommendation_description() {
		return recommendation_description;
	}

	public void setRecommendation_description(String recommendation_description) {
		this.recommendation_description = recommendation_description;
	}

	public Integer getCweid() {
		return cweid;
	}

	public void setCweid(Integer cweid) {
		this.cweid = cweid;
	}

	public String getCwename() {
		return cwename;
	}

	public void setCwename(String cwename) {
		this.cwename = cwename;
	}

	public Boolean getCwepcirelated() {
		return cwepcirelated;
	}

	public void setCwepcirelated(Boolean cwepcirelated) {
		this.cwepcirelated = cwepcirelated;
	}

	public String getCwe_description() {
		return cwe_description;
	}

	public void setCwe_description(String cwe_description) {
		this.cwe_description = cwe_description;
	}

	public String getCia_impact() {
		return cia_impact;
	}

	public void setCia_impact(String cia_impact) {
		this.cia_impact = cia_impact;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getFlaw_description() {
		return flaw_description;
	}

	public void setFlaw_description(String flaw_description) {
		this.flaw_description = flaw_description;
	}

	public Integer getExploitLevel() {
		return exploitLevel;
	}

	public void setExploitLevel(Integer exploitLevel) {
		this.exploitLevel = exploitLevel;
	}

	public String getFunctionprototype() {
		return functionprototype;
	}

	public void setFunctionprototype(String functionprototype) {
		this.functionprototype = functionprototype;
	}

	public Integer getFunctionrelativelocation() {
		return functionrelativelocation;
	}

	public void setFunctionrelativelocation(Integer functionrelativelocation) {
		this.functionrelativelocation = functionrelativelocation;
	}

	public Integer getIssueid() {
		return issueid;
	}

	public void setIssueid(Integer issueid) {
		this.issueid = issueid;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public Boolean getPcirelated() {
		return pcirelated;
	}

	public void setPcirelated(Boolean pcirelated) {
		this.pcirelated = pcirelated;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSourcefile() {
		return sourcefile;
	}

	public void setSourcefile(String sourcefile) {
		this.sourcefile = sourcefile;
	}

	public String getSourcefilepath() {
		return sourcefilepath;
	}

	public void setSourcefilepath(String sourcefilepath) {
		this.sourcefilepath = sourcefilepath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getBuild_id() {
		return build_id;
	}

	public void setBuild_id(Long build_id) {
		this.build_id = build_id;
	}

	public Long getApp_id() {
		return app_id;
	}

	public void setApp_id(Long app_id) {
		this.app_id = app_id;
	}
	
}
