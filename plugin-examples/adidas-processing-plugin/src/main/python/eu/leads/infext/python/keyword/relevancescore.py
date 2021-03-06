'''
Created on Sep 21, 2014

@author: nonlinear
'''

class RelevanceScore(object):
    '''
    classdocs
    '''


    def getscoredlist(self,row,wordids):
        totalscore = 0
        # This is where you'll later put the scoring functions
        weights=[(1.0,self.frequencyscore(row)),
            (1.5,self.locationscore(row)),
            (1.5,self.distancescore(row))]
        for (weight,scores) in weights:
            totalscore+=weight*scores
        return totalscore
    
    
    def normalizescores(self,scores,smallIsBetter=0):
        vsmall=0.00001 # Avoid division by zero errors
        if smallIsBetter:
            minscore=min(scores.values( ))
            return dict([(u,float(minscore)/max(vsmall,l)) for (u,l) \
                in scores.items( )])
        else:
            maxscore=max(scores.values( ))
            if maxscore==0: maxscore=vsmall
            return dict([(u,float(c)/maxscore) for (u,c) in scores.items( )])


    def frequencyscore(self,row):
        count=0
        for row in rows: counts[row[0]]+=1
        return self.normalizescores(counts)


    def locationscore(self,rows):
        locations=dict([(row[0],1000000) for row in rows])
        for row in rows:
            loc=sum(row[1:])
            if loc<locations[row[0]]: locations[row[0]]=loc
        return self.normalizescores(locations,smallIsBetter=1)
    

    def distancescore(self,rows):
        # If there's only one word, everyone wins!
        if len(rows[0])<=2: return dict([(row[0],1.0) for row in rows])
        # Initialize the dictionary with large values
        mindistance=dict([(row[0],1000000) for row in rows])
        for row in rows:
            dist=sum([abs(row[i]-row[i-1]) for i in range(2,len(row))])
            if dist<mindistance[row[0]]: mindistance[row[0]]=dist
        return self.normalizescores(mindistance,smallIsBetter=1)
