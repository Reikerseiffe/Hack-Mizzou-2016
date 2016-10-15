//
//  SongCellController.swift
//  Hack Mizzou 2016
//
//  Created by Reiker Seiffe on 10/14/16.
//  Copyright © 2016 Reiker R Seiffe. All rights reserved.
//

import UIKit

class SongCellController: UITableViewCell {
    
    @IBOutlet weak var songLabel: UILabel!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var downButton: UIButton!
    @IBOutlet weak var upButton: UIButton!
    
    let webWorker = WebWorker()
    
    var index:Int = 0
    var roomID:Int = 0

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    
    @IBAction func voteDown(sender: AnyObject) {
        //send songID and downvote to API
        print("I downvoted")
        webWorker.downvote(roomID, index: index + 1)
        
        sleep(1)
        
        NSNotificationCenter.defaultCenter().postNotificationName("load", object: nil)

    }
    
    @IBAction func voteUp(sender: AnyObject) {
        //send songID and upvote to API
        print("I upvoted")
        
        webWorker.upvote(roomID, index: index + 1)
        
        sleep(1)
        
        NSNotificationCenter.defaultCenter().postNotificationName("load", object: nil)
    }
    

}
