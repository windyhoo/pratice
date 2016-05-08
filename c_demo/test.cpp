#include <iostream>
#include <set>
#include <string>
#include <sstream>

using namespace std;

class Solution {
public:
    string getHint(string secret, string guess) {
        int a=0;
        int b=0;
        
        int len=4;
        int mask[4]={0};
        
        set<char> setchar;
        for(int i=0;i<len;++i) {
            if(guess[i]==secret[i]) {
                mask[i]=1;
            }
        }
        
        for(int i=0;i<len;++i) {
        	cout<<mask[i]<<" ";
        }
        cout<<endl;
        //11
        //10
        
        //"1807"
        //"7810"
        
        //1a1b vs 1a3b
        //guess
        //secret
        for(int i=0;i<len;++i) {
        	cout<<"i:"<<i<<endl;
            for(int j=0;j<len;++j) {
                if(secret[j]==guess[i] && mask[j]==0) {
                	cout<<"j:"<<j<<endl;
                    mask[j]=2;
                    break;
                }
            }

           	cout<<"debug i:"<<i<<endl;
           	cout<<"debug i value:"<<mask[i+1]<<endl;
        }
        
        for(int i=0;i<len;++i) {
            if(mask[i]==1) {
                ++a;
            } else if(mask[i]==2) {
                ++b;
            }
        }
        
        stringstream ssa;
        ssa << a;
        stringstream ssb;
        ssb << b;

        return ssa.str()+"A"+ssb.str()+"B";
    }
};

void test1() {
	int a[10];
    //int a[10]={0};
    for(int i=0;i<10;++i) {
        cout<<a[i]<<endl;
    }
}

class A{
   int i;
};
class B{
   A *p;
public:
   B(){p=new A;}
   ~B(){delete p;}
};
void sayHello(B b){
}

int main() {
	// Solution *so= new Solution();
	// cout<<so->getHint("1807","7810")<<endl;
   B b;
   sayHello(b);

	return 0;
}
