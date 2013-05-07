dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    password = "zc@r10c@"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/zcarioca-dev"
            username = "zcarioca-dev"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/zcarioca-test"
            username = "zcarioca-test"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/zcarioca"
            username = "zcarioca"
/*
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
*/
        }
    }
}
