//
//  ToggleTableViewCell.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface ToggleTableViewCell : NSTableCellView

@property (weak) IBOutlet NSButton *toggleButton;

- (void)setType:(NSInteger)type;
- (NSInteger) type;
- (NSString *)getString;
- (void)setString:(NSString *)string;
- (NSInteger)getRow;

@end
