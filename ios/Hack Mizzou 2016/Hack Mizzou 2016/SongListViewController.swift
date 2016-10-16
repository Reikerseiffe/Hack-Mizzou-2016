//
//  SongListViewController.swift
//  Hack Mizzou 2016
//
//  Created by Reiker Seiffe on 10/14/16.
//  Copyright © 2016 Reiker R Seiffe. All rights reserved.
//

import UIKit


class SongListViewController: UIViewController {
    
    @IBOutlet weak var currentSongLabel: UILabel!
    @IBOutlet weak var currentArtistLabel: UILabel!
    @IBOutlet weak var currentScoreLabel: UILabel!
    @IBOutlet weak var currentArtwork: UIImageView!
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var bottomView: UIView!
    
    let webWorker = WebWorker()
    
    var songArray:NSArray = []
    var roomID:Int = 0
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //print("After Segue")
        //print(songArray)
        
        let data = NSData(contentsOfURL: NSURL(string: (songArray[0]["vcCoverArt"] as! String))!)
        //print(data);
        if let data = data{
            currentArtwork.image = UIImage(data: data)
        }
        
        //bottomView.layer.borderWidth = 2
        //bottomView.layer.borderColor = UIColor.blackColor().CGColor
        
        
        
        currentSongLabel.text = songArray[0]["vcName"] as? String
        currentArtistLabel.text = songArray[0]["vcArtist"] as? String
        if let score:String = String(songArray[0]["nRepScore"]!!){
            currentScoreLabel.text = "Score: " + score
        }
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector:#selector(SongListViewController.reloadData), name:UIApplicationWillEnterForegroundNotification, object: nil)
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(SongListViewController.loadList(_:)),name:"load", object: nil)

        // Do any additional setup after loading the view.
    }
    
    func loadList(notification: NSNotification){
        //load data here
        reloadData()
    }
    
    func reloadData(){
        print("Will reload data")
        
        print(roomID)
        
        songArray = []
        
        webWorker.joinRoom(roomID){
            (result, error) in
            
            print("Result: ")
            print(result!)
            
            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                self.songArray = result!
                self.tableView.reloadData()
            })
            //self.songArray = result!
            //self.tableView.reloadData()
        }
        
        
    }
    
    
    override func viewWillAppear(animated: Bool) {
        //print("looking at the page")
    }
    
    override func viewDidAppear(animated: Bool) {
        print("looking at the page")
        
        
        
        
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        let num = songArray.count - 1
        return num
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("songCell", forIndexPath: indexPath) as! SongCellController
        
        //cell.configureWithValue(UIColor.clearColor())
        let data = songArray[indexPath.row + 1]
        
        cell.index = indexPath.row
        cell.roomID = roomID
        
        cell.songLabel.text = data["vcName"] as? String
        cell.artistLabel.text = data["vcArtist"] as? String
        cell.scoreLabel.text = "Score: " + String(data["nRepScore"]!!)
        
        /*if let score:String = String(data["currentScore"]!!){
            print(score)
            cell.scoreLabel.text = score
        }*/


        //cell.scoreLabel.text = dataArray[indexPath.row][]

        
        
        // Configure the cell...
        
        return cell
    }

    
    
    
    
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
