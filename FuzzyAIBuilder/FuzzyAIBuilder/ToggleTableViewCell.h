//
//  ToggleTableViewCell.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface ToggleTableViewCell : NSTableCellView

@property NSInteger type;
@property NSInteger currIndex;
@property NSArray *operators;
@property (weak) IBOutlet NSButton *toggleButton;

- (void)setMode:(NSInteger) mode;
- (NSString *)getString;

@end
