import time
import traceback


classpaths = ['../../processor/queryProcessor/bin/',
              '../../solr/dist/',
              '../../solr/dist/solrj-lib/']
java_cp_arg = "-cp "+":".join(classpaths)



queryMapperParams = {'filename':'QueryMapper',
                     'destination_path':'docs/',
                     }
queryMapperFilename = queryMapperParams['destination_path']+"queryOutput.json"

queryMapperParams.update({'cp':java_cp_arg})

queryMapperCmd = "java %(cp)s %(filename)s %(destination_path)s %(qtype)s %(fiveW)s %(col2)s \"%(noun)s\" %(last_col)s" 






queryFacetParams = {'filename':'QueryFacet',
                    'destination_path':'docs/',
                    }
queryFacetParams.update({'cp':java_cp_arg})

queryFacetCmd = "java %(cp)s %(filename)s %(destination_path)s %(qtype)s" 

queryFacetFilename = {'person':queryMapperParams['destination_path']+'facetPerson.json',
                      'places':queryMapperParams['destination_path']+'facetPlaces.json',
                      'film':queryMapperParams['destination_path']+'facetFilm.json'}




queryFacetExpParams = {'filename':'QueryFacetExpansion',
                    'destination_path':'docs/',
                    }
queryFacetExpParams.update({'cp':java_cp_arg})

queryFacetExpCmd = "java %(cp)s %(filename)s %(destination_path)s %(qtype)s %(query)s"

queryFacetExpFilename = queryFacetExpParams['destination_path']+"facetExpansion.json"




queryVerticalParams = {'filename':'QueryMltVerExp',
                     'destination_path':'docs/',
                     }
queryVerticalFilename = queryVerticalParams['destination_path']+"mltVerExp.json"

queryVerticalParams.update({'cp':java_cp_arg})

queryVerticalCmd = "java %(cp)s %(filename)s %(destination_path)s %(qtype)s %(fiveW)s %(col2)s \"%(noun)s\" %(last_col)s" 




queryHorizontalParams = {'filename':'QueryMltHorExp',
                     'destination_path':'docs/',
                     }
queryHorizontalFilename = queryHorizontalParams['destination_path']+"mltHorExp.json"

queryHorizontalParams.update({'cp':java_cp_arg})

queryHorizontalCmd = "java %(cp)s %(filename)s %(destination_path)s %(qtype)s %(fiveW)s %(col2)s \"%(noun)s\" %(last_col)s" 




responseMessages = {'noresults': 'Sorry! No Results were found for your query!',
            'exception': 'Oops! There was an exception! Try again. ',
            'yesresults': "%(name)s's %(field)s is/are %(answers)s",}


date_list = ['birthdate', 'deathdate']

questionParams = {'person':
                            {'birthdate':('when','was','born'),
                             'nationality':('what','is','nationality'),
                             'occupation':('what','is','occupation'),
                             'yearsactive':('what','is','yearsactive'),
                             'birthplace':('what','is','birthplace'),
                             'birthname':('what','was','birthname'),
                             'education':('what','is','education'),
                             'deathplace':('where','did','die'),
                             'awards':('what','are','awards'),
                             'weight':('what','is','weight'),
                             'height':('what','is','height'),
                             'religion':('what','is','religion'),
                             'parents':('who','are','parents'),
                             'caption':('what','is','caption'),
                             'deathdate':('when','did','die'),
                             'partner':('who','is','partner'),
                             'title':('what','is','title'),
                             'spouse':('who','is','spouse'),
                             'website':('what','is','website'),
                             'residence':('where','does','live'),
                             'knownfor':('what','is','knownfor'),
                             'role':('what','is','role'),
                             'networth':('what','is','networth'),
                             'credits':('what','is','credits'),
                             'othernames':('what','are','othernames'),
                             'party':('what','is','party'),
                             'family':('who','are','family'),
                             'successor':('who','is','successor'),
                             'predecessor':('who','is','predecessor'),
                             'salary':('what','is','salary'),
                             'alias':('what','is','alias'),
                             'employer':('who','is','employer'),
                             'tribe':('what','is','tribe'),
                             },
                    'film':{
                            'director':('who','is','director'),
                            'producer':('who','is','producer'),
                            'writer':('who','is','writer'),
                            'scriptwriter':('who','is','scriptwriter'),
                            'editor':('who','is','editor'),
                            'narrator':('who','is','narrator'),
                            'distributor':('who','is','distributor'),
                            'cinematographer':('who','is','cinematographer'),
                            'filmeditor':('who','is','filmeditor'),
                            'music':('who','is','music'),
                            'starring':('who','all','starring'),
                            'story':('what','is','story'),
                            'basedon':('what','was','basedon'),
                            'studio':('where','was','shot'),
                            'language':('what','is','language'),
                            'releasedate':('when','was','released'),
                            'gross':('what','was','revenue'),
                            'budget':('what','was','budget'),
                    },
                    'places':{
                            'leader':('who','is','leader'),
                            'founder':('who','is','founder'),
                            'state':('where','is','located'),
                            'established':('where','is','established'),
                            'settlementtype':('what','is','settlementtype'),
                            'nativename':('what','is','nativename'),
                            'nickname':('what','is','nickname'),
                            'country':('what','is','country'),
                            'county':('what','is','county'),
                            'coordinates':('what','is','coordinates'),
                            'area':('what','is','area'),
                            'population':('what','is','population'),
                            'populationdensity':('what','is','populationdensity'),
                            'timezone':('what','is','timezone'),
                            'postalcode':('what','is','postalcode'),
                            'motto':('what','is','motto'),
                            'website':('what','is','website'),
                            
                    }

                    }

facetBy = {'person':'occupation',
           'places':'state',
           'film':'director',}



def convertDate(date_in_z):
    try:
        return time.strftime("%d %B %Y", time.strptime(date_in_z, "%Y-%m-%dT%H:%M:%SZ")), ""
    except:
        print traceback.format_exc()    
        return "", "Exception"
    
