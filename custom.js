
     let count=0;
     let prev=   document.querySelector('#prev');
     let next=   document.querySelector('#next');
         document.querySelector('#prev').addEventListener('dblclick', ()=>{
             
             prev.classList.add('show-seek');
             backward()
             setTimeout(() => {
                 prev.classList.remove('show-seek');
             }, 2000);
        });
 
         document.querySelector('#next').addEventListener('dblclick',()=>{
             
            next.classList.add('show-seek');
            forward()
                 
            setTimeout(() => {
                next.classList.remove('show-seek');
            }, 2000);   
        });
 
         let videoplayer=videojs('my-video',{
             controls:true,
              loop:false,
             playbackRates:[0.5,1,1.5,2,2.5,3,3.5],
             
             });
             videoplayer.seekButtons({
                 forward: 30,
                 back: 10
             })
 
 
             const  skip=(time)=>
             {
                 videoplayer.currentTime(videoplayer.currentTime()+time);
             }
 
             const forward=()=>{
             skip(30)
             }
 
             const backward=()=>{
                 skip(-10)
             }
             const nextplay=()=>
             {

                videoplayer.src({
                    src: "https://content.madfunny.co.za/videos/big_buck_bunny_720p_1mb.mp4",
                    type:'video/mp4'

                })

                videoplayer.play();

             }
               const getTitle=()=>
               {
                   return "NExt Play ! ";
               } 
                const nextt=()=>{
                    alert("END");
                }

             videoplayer.upnext({
                timeout : 5000,
                getTitle : getTitle,
                next : nextt
              });