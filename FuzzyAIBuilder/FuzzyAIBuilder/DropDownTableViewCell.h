//
//  DropDownTableViewCell.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface DropDownTableViewCell : NSTableCellView

@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet NSPopUpButton *popUp;
@end
