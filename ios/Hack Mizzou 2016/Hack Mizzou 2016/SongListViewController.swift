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
    
    var dataArray:NSArray = []
    
    
    
    let fakeData: NSDictionary = [
        "currentSong" : "Roses",
        "currentArtist" : "The Chain Smokers",
        "currentScore" : 42,
        "currentArtwork" : "https://www.google.com/search?q=album+cover+roses&espv=2&biw=1920&bih=1103&source=lnms&tbm=isch&sa=X&ved=0ahUKEwi66Y-h5tvPAhWk24MKHaq9AxwQ_AUIBigB#imgrc=YQeLV0hH6mpJfM%3A"
    ]
    
    let fakeData2: NSDictionary = [
        "currentSong" : "Roses",
        "currentArtist" : "The Chain Smokers",
        "currentScore" : 42,
        "currentArtwork" : "https://www.google.com/search?q=album+cover+roses&espv=2&biw=1920&bih=1103&source=lnms&tbm=isch&sa=X&ved=0ahUKEwi66Y-h5tvPAhWk24MKHaq9AxwQ_AUIBigB#imgrc=YQeLV0hH6mpJfM%3A"
    ]
    
    let fakeData3: NSDictionary = [
        "currentSong" : "Roses",
        "currentArtist" : "The Chain Smokers",
        "currentScore" : 35,
        "currentArtwork" : "https://www.google.com/search?q=album+cover+roses&espv=2&biw=1920&bih=1103&source=lnms&tbm=isch&sa=X&ved=0ahUKEwi66Y-h5tvPAhWk24MKHaq9AxwQ_AUIBigB#imgrc=YQeLV0hH6mpJfM%3A"
    ]
    
    let fakeData4: NSDictionary = [
        "currentSong" : "Roses",
        "currentArtist" : "The Chain Smokers",
        "currentScore" : 52,
        "currentArtwork" : "https://www.google.com/search?q=album+cover+roses&espv=2&biw=1920&bih=1103&source=lnms&tbm=isch&sa=X&ved=0ahUKEwi66Y-h5tvPAhWk24MKHaq9AxwQ_AUIBigB#imgrc=YQeLV0hH6mpJfM%3A"
    ]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dataArray = [fakeData2, fakeData3, fakeData4]
        
        currentSongLabel.text = fakeData["currentSong"] as? String
        currentArtistLabel.text = fakeData["currentArtist"] as? String
        if let score:String = String(fakeData["currentScore"]!){
            currentScoreLabel.text = score
        }
        
        

        // Do any additional setup after loading the view.
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
        return dataArray.count
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("songCell", forIndexPath: indexPath) as! SongCellController
        
        //cell.configureWithValue(UIColor.clearColor())
        let data = dataArray[indexPath.row]
        
        
        cell.songLabel.text = data["currentSong"] as? String
        cell.artistLabel.text = data["currentArtist"] as? String
        
        if let score:String = String(data["currentScore"]!!){
            print(score)
            cell.scoreLabel.text = score
        }


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
