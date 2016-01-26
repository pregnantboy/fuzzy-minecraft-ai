//
//  RuleNumTableViewCell.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface RuleNumTableViewCell : NSTableCellView
@property (weak) IBOutlet NSTextField *number;

- (void)setNum:(NSInteger)num;
- (NSInteger)getNum;

@end
